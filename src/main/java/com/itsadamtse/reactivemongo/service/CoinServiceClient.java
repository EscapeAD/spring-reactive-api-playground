package com.itsadamtse.reactivemongo.service;

import com.itsadamtse.reactivemongo.dto.Coin;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.List;

@Slf4j
public class CoinServiceClient {
    // using public info api
    // hardcode query strings
    //https://www.coingecko.com/en/api#explore-api
    public static final String COIN_SERVICE = "https://api.coingecko.com/";
    public static final String GET_ALL_COINS = "api/v3/coins/list";
    public static final String GET_COIN = "api/v3/coins/{id}";

    private WebClient webClient;

    public CoinServiceClient(WebClient webClient){
        this.webClient = webClient;
    }
    public List<Coin> retrieveAllCoins(){
        // https://api.coingecko.com/?include_platform=false
        return webClient.get().uri(COIN_SERVICE + GET_ALL_COINS +"?include_platform=false")
                .retrieve()
                .bodyToFlux(Coin.class)
                .collectList()
                .block();
    }

    public Coin retrieveCoin(String coinIdc){
        try {
            return webClient.get().uri(COIN_SERVICE + GET_COIN +"?localization=false&tickers=false&market_data=false&community_data=false&developer_data=false&sparkline=false", coinIdc)
                    .retrieve()
                    .bodyToMono(Coin.class)
                    .block();
        } catch(WebClientResponseException ex){
            log.error("Error Response code is {} and body is {}", ex.getStatusCode(), ex.getResponseBodyAsString());
            log.error("WebClientResponseException retrieveCoin:", ex);
            throw ex;
        } catch(Exception ex){
            log.error("Exception retrieveCoin:", ex);
            throw ex;
        }
    }
}
