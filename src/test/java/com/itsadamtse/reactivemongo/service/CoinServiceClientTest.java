package com.itsadamtse.reactivemongo.service;

import com.itsadamtse.reactivemongo.dto.Coin;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.web.reactive.function.client.WebClient;
import java.util.List;

public class CoinServiceClientTest {
    private WebClient webClient = WebClient.create();

    CoinServiceClient coinServiceClient = new CoinServiceClient(webClient);

    @Test
    public void getAllCoin(){
        List<Coin> coinList = coinServiceClient.retrieveAllCoins();
        System.out.println("CoinList: " + coinList);
        Assertions.assertTrue(coinList.size() > 0);
    }
}
