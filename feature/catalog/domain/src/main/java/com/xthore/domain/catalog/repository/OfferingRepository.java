package com.xthore.domain.catalog.repository;

import com.xthore.domain.catalog.model.Offering;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface OfferingRepository {
    Flux<Offering> findAll();

    Mono<Offering> findByProduct(String product);

    Mono<Boolean> isAvailable(String product);
}

