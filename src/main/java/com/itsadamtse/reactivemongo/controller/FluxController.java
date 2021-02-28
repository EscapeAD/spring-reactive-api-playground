package com.itsadamtse.reactivemongo.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.time.Duration;

@RestController
@RequestMapping("/flux")
public class FluxController {

    @GetMapping("")
    public Flux<Integer> fluxResp(){
        return Flux.just(1,2,3,4)
                .delayElements(Duration.ofSeconds(1))
                .log();
    }

    /*
        stream using deprecated MediaType.APPLICATION_STREAM_JSON_VALUE
    */
    @GetMapping(value = "/stream", produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
    public Flux<Integer> fluxStreamResp(){
        return Flux.just(1,2,3,4)
                .delayElements(Duration.ofSeconds(1))
                .log();
    }

    /*
        stream using MediaType.TEXT_EVENT_STREAM_VALUE
    */
    @GetMapping(value = "/stringstream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> fluxStringStreamResp(){
        return Flux.just(1,2,3,4)
                .delayElements(Duration.ofSeconds(1))
                .map(it->String.format("Event%d", it))
                .log();
    }
}
