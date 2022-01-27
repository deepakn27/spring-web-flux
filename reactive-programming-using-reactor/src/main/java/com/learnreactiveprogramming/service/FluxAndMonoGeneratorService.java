package com.learnreactiveprogramming.service;

import reactor.core.Disposable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.List;
import java.util.Random;
import java.util.function.Function;

public class FluxAndMonoGeneratorService {

    public Flux<String> namesFlux() {
        return Flux.fromIterable(List.of("alex","chloe")).log();
    }

    public Mono<String> nameMono() {
        return Mono.just("Temple").log();
    }

    public Mono<List<String>> nameMono_FlatMap() {
        return Mono.just("Temple")
                .flatMap(s -> Mono.just(List.of(s.split(""))))
                .log();
    }

    public Flux<String> nameMono_FlatMapMany() {
        return Mono.just("Temple")
                .flatMapMany(splitArray)
                .log();
    }

    public Flux<String> namesFlux_FlatMap() {
        return Flux.fromIterable(List.of("alex","chloe"))
                .flatMap(splitArray_delay)
                .log();
    }

    public Flux<String> namesFlux_ConcatMap() {
        return Flux.fromIterable(List.of("alex","chloe"))
                .concatMap(splitArray_delay)
                .log();
    }

    public Flux<String> namesFlux_SwitchIfEmpty() {

        Flux<String> temp = Flux.fromIterable(List.of("ben","mike"))
                .concatMap(splitArray_delay)
                .log();
        return Flux.fromIterable(List.of("alex","chloe"))
                .transform(toUpperCase)
                .filter(s -> s.length() < 3)
                .switchIfEmpty(temp)
                .log();
    }

    Function<Flux<String>, Flux<String>> toUpperCase = (name) -> {
        return name.map(String::toUpperCase);
    };

    Function<String, Flux<String>> splitArray_delay = (name) -> {
        return Flux.fromArray(name.split(""))
                .delayElements(Duration.ofMillis(new Random().nextInt(1000)));
    };

    Function<String, Flux<String>> splitArray = (name) -> {
        return Flux.fromArray(name.split(""));
    };

    public Flux<String> nameConcat() {
        var abcFlux = Flux.just("A","B","C");
        var defFlux = Flux.just("D","E","F");
        return Flux.concat(abcFlux,defFlux).log();
    }

    public Flux<String> nameMerge() {
        var abcFlux = Flux.just("A","B","C").delayElements(Duration.ofMillis(100));
        var defFlux = Flux.just("D","E","F").delayElements(Duration.ofMillis(125));
        return Flux.merge(abcFlux,defFlux).log();
    }

    public Flux<String> nameZip() {
        var abcFlux = Flux.just("A","B","C");
        var defgFlux = Flux.just("D","E","F","G");
        var mnopqFlux = Flux.just("M","N","O","P","Q");
        return Flux.zip(abcFlux, defgFlux, mnopqFlux)
                .map(t3 -> t3.getT1() + t3.getT2() + t3.getT3())
                .log();
    }

    public static void main(String[] args) {
        FluxAndMonoGeneratorService fl = new FluxAndMonoGeneratorService();
        Flux.fromIterable(List.of("alex","chloe"))
                .flatMap(splitArray_delay_2).subscribe(System.out::println);
        System.out.println("Hello");
        Flux.fromIterable(List.of("alex","chloe"))
                .flatMap(name -> Flux.fromArray(name.split(""))).subscribe(System.out::println);
        Flux.fromIterable(List.of("alex","chloe"))
                .flatMap( name -> Flux.fromArray(name.split(""))
                                .delayElements(Duration.ofMillis(new Random().nextInt(1000)))
                        )
                .subscribe(System.out::println);

    }

    public static Function<String, Flux<String>> splitArray_delay_1 = (name) -> {
        return Flux.fromArray(name.split(""))
                .delayElements(Duration.ofMillis(new Random().nextInt(1000)));
    };

    public static Function<String, Flux<String>> splitArray_delay_2 = (name) -> {
        return Flux.fromArray(name.split(""));
    };
}
