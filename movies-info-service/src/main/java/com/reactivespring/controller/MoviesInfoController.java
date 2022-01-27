package com.reactivespring.controller;

import com.reactivespring.model.MovieInfo;
import com.reactivespring.service.MoviesInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@RestController
@RequestMapping("v1/movieinfos")
public class MoviesInfoController {

    @Autowired
    private MoviesInfoService moviesInfoService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<MovieInfo> saveMovieInfo(@RequestBody @Valid MovieInfo movieInfo) {
        return moviesInfoService.addMovieInfo(movieInfo).log();
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Flux<MovieInfo> getMovieInfo() {
        return moviesInfoService.findAll().log();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<MovieInfo> getMovieInfoById(@PathVariable String id) {
        return moviesInfoService.findById(id).log();
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<MovieInfo>> updateMovieInfoById(@RequestBody MovieInfo movieInfo, @PathVariable String id) {
        return moviesInfoService.updateMovieInfo(movieInfo,id).log()
                .map(returnedmovieInfo -> ResponseEntity.ok().body(returnedmovieInfo))
                .switchIfEmpty(Mono.just(ResponseEntity.notFound().build()));
    }

    @PutMapping()
    @ResponseStatus(HttpStatus.OK)
    public Flux<Integer> updateMovieInfoAll(@RequestBody MovieInfo movieInfo) {
        return moviesInfoService.updateMovieInfoAll(movieInfo).log();
    }
}
