package com.xthore.data.catalog.persistence;

import com.xthore.domain.catalog.model.Offering;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface OfferingPersistence {
    Flux<Offering> findAll();

    Mono<Offering> findByProduct(String product);

    Mono<Boolean> isAvailable(String product);
}

