package com.itsadamtse.reactivemongo.playground;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class FluxAndMonoTest {

    @Test
    public void fluxTest(){
        // Life Cycle Test
        Flux<String> stringFlux = Flux.just("kitten", "chicken", "dog")
                .concatWith((Flux.error(new RuntimeException("Exception boom badger"))))
                .concatWith(Flux.just("After Error?"))
                .log();
        stringFlux
                .subscribe(System.out::println,
                        (e)-> System.err.println("Exception:" + e),
                        ()-> System.out.println("Complete Event"));
    }

    @Test
    public void fluxElementTest(){
        // Test each Element
        Flux<String> stringFlux = Flux.just("kitten", "chicken", "dog")
                .log();
        StepVerifier.create(stringFlux)
                    .expectNext("kitten", "chicken", "dog") //can use single
                    .verifyComplete();
    }

    @Test
    public void fluxElementTest_WithErrorClass(){
        // Test error with throw class
        Flux<String> stringFlux = Flux.just("kitten", "chicken", "dog")
                .log();
        StepVerifier.create(stringFlux)
                    .expectNext("meow")
                    .expectError(RuntimeException.class)
                    .verify();
    }

    @Test
    public void fluxElementTest_WithErrorMessage(){
        // Test error with throw message
        Flux<String> stringFlux = Flux.just("kitten")
                .concatWith(Flux.error(new RuntimeException("BROKEN RANDOM MSG")))
                .log();
        StepVerifier.create(stringFlux)
                    .expectNext("kitten")
                    .expectErrorMessage("BROKEN RANDOM MSG")
                    .verify();
    }

    @Test
    public void fluxElementCountTest_WithError(){
        // Test each Element by count, then error
        Flux<String> stringFlux = Flux.just("kitten", "chicken", "dog")
                .concatWith(Flux.error(new RuntimeException("BROKEN RANDOM MSG")))
                .log();
        StepVerifier.create(stringFlux)
                    .expectNextCount(3)
                    .expectErrorMessage("BROKEN RANDOM MSG")
                    .verify();
    }

    @Test
    public void monoTest(){
        Mono<String> stringMono = Mono.just("puppy");
        StepVerifier.create(stringMono.log())
                .expectNext("puppy")
                .verifyComplete();
    }

    @Test
    public void monoErrorTest(){
        StepVerifier.create(Mono.error(new RuntimeException("Error Single")).log())
                .expectError(RuntimeException.class)
                .verify();
    }
}
