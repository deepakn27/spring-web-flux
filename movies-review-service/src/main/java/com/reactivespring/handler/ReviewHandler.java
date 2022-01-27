package com.reactivespring.handler;

import com.mongodb.internal.connection.Server;
import com.reactivespring.domain.Review;
import com.reactivespring.repository.ReviewReactiveRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.function.Function;

@Component
public class ReviewHandler {

    @Autowired
    private ReviewReactiveRepository reviewReactiveRepository;

    public Mono<ServerResponse> addReview(ServerRequest request) {
        return request.bodyToMono(Review.class)
                .flatMap(review -> {
                    return ServerResponse.status(HttpStatus.CREATED).body(reviewReactiveRepository.save(review),Review.class);
                });
    }

    public Mono<ServerResponse> getReview(ServerRequest request) {
        var param = request.queryParam("movieInfoId");
        var infoId = param.get();
        return ServerResponse.ok().body(reviewReactiveRepository.findReviewsByMovieInfoId(Long.valueOf(infoId)),Review.class);
    }

    public Mono<ServerResponse> updateReview(ServerRequest request) {
        var reviewId = request.pathVariable("id");
        var body = request.bodyToMono(Review.class);
        var existingReview = reviewReactiveRepository.findById(reviewId);
        return existingReview
                .flatMap(review -> body.map(requestReview -> {
                                            review.setComment(requestReview.getComment());
                                            return review;
                         })
                        .flatMap(review1 -> {
                            review1.setComment("Random");
                            return Mono.just(review);
                        })
                        .flatMap(savedReview ->  ServerResponse.ok().bodyValue(savedReview)));
    }

    public Mono<ServerResponse> deletMovieReview(ServerRequest request) {
        var id = request.pathVariable("id");
        return reviewReactiveRepository.deleteById(id)
                .then(ServerResponse.noContent().build());
    }
}
