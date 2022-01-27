package com.reactivespring.service;

import com.reactivespring.model.MovieInfo;
import com.reactivespring.repository.MovieInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class MoviesInfoService {

    @Autowired
    MovieInfoRepository movieInfoRepository;

    public Mono<MovieInfo> addMovieInfo(MovieInfo movieInfo) {
        return movieInfoRepository.save(movieInfo);
    }

    public Flux<MovieInfo> findAll() {
        return movieInfoRepository.findAll();
    }

    public Mono<MovieInfo> findById(String id) {
        return movieInfoRepository.findById(id);
    }

    public Mono<MovieInfo> updateMovieInfo(MovieInfo updatedmovieInfo, String id) {
       return movieInfoRepository.findById(id).log()
                .flatMap(movieInfo -> {
                    movieInfo.setCast(updatedmovieInfo.getCast());
                    movieInfo.setYear(updatedmovieInfo.getYear());
                    movieInfo.setName(updatedmovieInfo.getName());
                    return movieInfoRepository.save(movieInfo);
                });
    }

    public Flux<Integer> updateMovieInfoAll(MovieInfo updatedmovieInfo) {
        return movieInfoRepository.findAll().log()
                .flatMap(movieInfo -> {
                    System.out.println("PPPP" + movieInfo);
                    return Flux.just(movieInfo.getYear());
                });
    }
}
