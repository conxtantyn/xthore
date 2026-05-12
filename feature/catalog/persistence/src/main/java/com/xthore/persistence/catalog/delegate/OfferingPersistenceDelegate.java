package com.xthore.persistence.catalog.delegate;

import com.xthore.data.catalog.persistence.OfferingPersistence;
import com.xthore.domain.catalog.model.Offering;

import com.xthore.persistence.catalog.datasource.OfferingDatasource;
import com.xthore.persistence.catalog.datasource.ProductDatasource;
import com.xthore.persistence.catalog.mapper.OfferingMapper;

import org.springframework.stereotype.Service;

import java.util.UUID;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class OfferingPersistenceDelegate implements OfferingPersistence {
    private final OfferingDatasource offeringDatasource;
    private final ProductDatasource productDatasource;

    public OfferingPersistenceDelegate(
            OfferingDatasource offeringDatasource,
            ProductDatasource productDatasource
    ) {
        this.offeringDatasource = offeringDatasource;
        this.productDatasource = productDatasource;
    }

    @Override
    public Flux<Offering> findAll() {
        return offeringDatasource.findAll().map(OfferingMapper::mapToDomain);
    }

    @Override
    public Mono<Offering> findByProduct(String product) {
        return offeringDatasource.findByProductUuid(UUID.fromString(product))
            .map(OfferingMapper::mapToDomain);
    }

    @Override
    public Mono<Boolean> isAvailable(String product) {
        return productDatasource.existsByProductOfferingUuid(UUID.fromString(product));
    }
}
