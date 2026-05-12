package com.xthore.persistence.catalog.delegate;

import com.xthore.common.model.Paging;
import com.xthore.data.catalog.persistence.ProductPersistence;
import com.xthore.domain.catalog.model.Product;

import com.xthore.persistence.catalog.datasource.ProductDatasource;
import com.xthore.persistence.catalog.mapper.ProductMapper;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Mono;

@Service
public class ProductPersistenceDelegate implements ProductPersistence {
    private final ProductDatasource datasource;

    public ProductPersistenceDelegate(ProductDatasource datasource) {
        this.datasource = datasource;
    }

    @Override
    public Mono<Product> find(String uuid) {
        return datasource.findByProductOfferingUuid(java.util.UUID.fromString(uuid))
            .map(ProductMapper::mapToDomain);
    }

    @Override
    public Paging.Response<Product> findAll(Paging.Page page) {
        PageRequest pageable = PageRequest.of(page.index(), page.size());
        return new Paging.Response<>(
            datasource.count()
                .map(total -> new Paging.Pageable(
                    page.index(),
                    page.size(),
                    (int) Math.ceil((double) total / page.size()),
                    total
                )),
            datasource.findAllBy(pageable).map(ProductMapper::mapToDomain)
        );
    }

    @Override
    public Paging.Response<Product> findAllByOffering(String offering, Paging.Page page) {
        PageRequest pageable = PageRequest.of(page.index(), page.size());
        return new Paging.Response<>(
            datasource.countAllByOffering(offering)
                .map(total -> new Paging.Pageable(
                    page.index(),
                    page.size(),
                    (int) Math.ceil((double) total / page.size()),
                    total
                )),
            datasource.findAllByOffering(offering, pageable).map(ProductMapper::mapToDomain)
        );
    }
}
