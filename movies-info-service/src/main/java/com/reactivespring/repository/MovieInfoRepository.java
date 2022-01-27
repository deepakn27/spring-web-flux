package com.reactivespring.repository;

import com.reactivespring.model.MovieInfo;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface MovieInfoRepository extends ReactiveMongoRepository<MovieInfo,String> {
}
