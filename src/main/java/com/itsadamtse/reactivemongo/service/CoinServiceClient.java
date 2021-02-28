package com.itsadamtse.reactivemongo.service;

import com.itsadamtse.reactivemongo.dto.Coin;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

public class CoinServiceClient {
    // using public info api
    //https://www.coingecko.com/en/api#explore-api
    public static final String COIN_SERVICE = "https://api.coingecko.com/";
    public static final String GET_ALL_COINS = "api/v3/coins/list";

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
}
