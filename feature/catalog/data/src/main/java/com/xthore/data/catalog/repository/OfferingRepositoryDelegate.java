package com.xthore.data.catalog.repository;

import com.xthore.data.catalog.persistence.OfferingPersistence;
import com.xthore.domain.catalog.model.Offering;
import com.xthore.domain.catalog.repository.OfferingRepository;

import org.springframework.stereotype.Repository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public class OfferingRepositoryDelegate implements OfferingRepository {
    private final OfferingPersistence persistence;

    public OfferingRepositoryDelegate(OfferingPersistence persistence) {
        this.persistence = persistence;
    }

    @Override
    public Flux<Offering> findAll() {
        return persistence.findAll();
    }

    @Override
    public Mono<Offering> findByProduct(String product) {
        return persistence.findByProduct(product);
    }

    @Override
    public Mono<Boolean> isAvailable(String product) {
        return persistence.isAvailable(product);
    }
}
