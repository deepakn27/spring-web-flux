package com.learnreactiveprogramming.service;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FluxAndMonoGeneratorServiceTest {

    FluxAndMonoGeneratorService fl = new FluxAndMonoGeneratorService();

    @Test
    void namesFlux() {
        Flux namesFlux = fl.namesFlux();
        StepVerifier.create(namesFlux)
                .expectNext("alex")
                .expectNextCount(1)
                .verifyComplete();
    }

    @Test
    void namesFlatMapFlux() {
        StepVerifier.create(fl.namesFlux_FlatMap())
                .expectNext("a","l","e","x","c","h")
                .expectNextCount(3)
                .verifyComplete();
    }

    @Test
    void namesFlatMapFlux_Async() {
        StepVerifier.create(fl.namesFlux_FlatMap())
                //.expectNext("a","l","e","x","c","h")
                .expectNextCount(9)
                .verifyComplete();
    }

    @Test
    void namesConcatMapFlux_Async() {
        StepVerifier.create(fl.namesFlux_ConcatMap())
                .expectNext("a","l","e","x","c","h")
                .expectNextCount(3)
                .verifyComplete();
    }

    @Test
    void namesMonoFlatMap() {
        StepVerifier.create(fl.nameMono_FlatMap())
                .expectNext(List.of("T","e","m","p","l","e"))
                .expectComplete()
                .verify();
    }

    @Test
    void namesMonoFlatMapMany() {
        StepVerifier.create(fl.nameMono_FlatMapMany())
                .expectNext("T","e","m","p","l","e")
                .expectComplete()
                .verify();
    }

    @Test
    void namesSwitchIfEmpty() {
        StepVerifier.create(fl.namesFlux_SwitchIfEmpty())
                .expectNext("b","e","n","m","i","k","e")
                .expectComplete()
                .verify();
    }

    @Test
    void nameConcat() {
        StepVerifier.create(fl.nameConcat())
                .expectNext("A","B","C","D","E","F")
                .verifyComplete();
    }

    @Test
    void nameMerge() {
        StepVerifier.create(fl.nameMerge())
                .expectNext("A","D","B","E","C","F")
                .verifyComplete();
    }
}