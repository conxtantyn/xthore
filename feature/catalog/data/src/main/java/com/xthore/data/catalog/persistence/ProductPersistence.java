package com.xthore.data.catalog.persistence;

import com.xthore.common.model.Paging;
import com.xthore.domain.catalog.model.Product;

import reactor.core.publisher.Mono;

public interface ProductPersistence {
    Mono<Product> find(String uuid);

    Paging.Response<Product> findAll(Paging.Page page);

    Paging.Response<Product> findAllByOffering(String offering, Paging.Page page);
}

