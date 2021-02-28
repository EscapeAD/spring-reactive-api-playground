package com.itsadamtse.reactivemongo.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.EntityExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.Arrays;
import java.util.List;


@ExtendWith(SpringExtension.class)
@WebFluxTest(controllers = FluxController.class)
public class FluxControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    public void fluxTestByElement(){
       Flux<Integer> integerFlux = webTestClient.get().uri("/flux")
               .accept(MediaType.APPLICATION_JSON_UTF8)
               .exchange()
               .expectStatus().isOk()
               .returnResult(Integer.class)
               .getResponseBody();

        StepVerifier.create(integerFlux)
                .expectSubscription()
                .expectNext(1)
                .expectNext(2)
                .expectNext(3)
                .expectNext(4)
                .verifyComplete();
    }

    @Test
    public void fluxTestByCount(){
       webTestClient.get().uri("/flux")
               .accept(MediaType.APPLICATION_JSON)
               .exchange()
               .expectStatus().isOk()
               .expectHeader().contentType(MediaType.APPLICATION_JSON)
               .expectBodyList(Integer.class)
               .hasSize(4);
    }

    @Test
    public void fluxTestByEntity(){
       List<Integer> expectedList = Arrays.asList(1,2,3,4);
       EntityExchangeResult<List<Integer>> exchangeResult = webTestClient.get().uri("/flux")
               .accept(MediaType.APPLICATION_JSON)
               .exchange()
               .expectStatus().isOk()
               .expectHeader().contentType(MediaType.APPLICATION_JSON)
               .expectBodyList(Integer.class)
               .returnResult();
        Assertions.assertEquals(expectedList, exchangeResult.getResponseBody());
    }

    @Test
    public void fluxTestByConsume(){
       List<Integer> expectedList = Arrays.asList(1,2,3,4);
       webTestClient.get().uri("/flux")
               .accept(MediaType.APPLICATION_JSON)
               .exchange()
               .expectStatus().isOk()
               .expectHeader().contentType(MediaType.APPLICATION_JSON)
               .expectBodyList(Integer.class)
               .consumeWith((response) -> {
                   Assertions.assertEquals(expectedList, response.getResponseBody());
               });
    }

    @Test
    public void fluxStream(){
        // infinite stream test
        Flux<Long> streamFlux = webTestClient.get().uri("/flux/stream")
                .accept(MediaType.APPLICATION_STREAM_JSON)
                .exchange()
                .expectStatus().isOk()
                .returnResult(Long.class)
                .getResponseBody();


        StepVerifier.create(streamFlux)
                .expectNext(0L)
                .expectNext(1L)
                .expectNext(2L)
                .thenCancel()
                .verify();
    }

    @Test
    public void fluxEventStringStream(){
        Flux<String> stringFlux = webTestClient.get().uri("/flux/stringstream")
                .accept(MediaType.TEXT_EVENT_STREAM)
                .exchange()
                .expectStatus().isOk()
                .returnResult(String.class)
                .getResponseBody();

        StepVerifier.create(stringFlux)
                .expectNext("Event1")
                .expectNext("Event2")
                .expectNext("Event3")
                .thenCancel()
                .verify();
    }
}
