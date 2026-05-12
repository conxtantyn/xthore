package com.xthore.data.catalog.repository;

import com.xthore.common.model.Paging;
import com.xthore.data.catalog.persistence.ProductPersistence;
import com.xthore.domain.catalog.model.Product;
import com.xthore.domain.catalog.repository.ProductRepository;

import org.springframework.stereotype.Repository;

import reactor.core.publisher.Mono;

@Repository
public class ProductRepositoryDelegate implements ProductRepository {
    private final ProductPersistence persistence;

    public ProductRepositoryDelegate(ProductPersistence persistence) {
        this.persistence = persistence;
    }

    @Override
    public Mono<Product> find(String uuid) {
        return persistence.find(uuid);
    }

    @Override
    public Paging.Response<Product> findAll(Paging.Page page) {
        return persistence.findAll(page);
    }

    @Override
    public Paging.Response<Product> findAllByOffering(String offering, Paging.Page page) {
        return persistence.findAllByOffering(offering, page);
    }
}
