package com.xthore.persistence.catalog.datasource;

import com.xthore.persistence.catalog.entity.ProductEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Repository
public interface ProductDatasource extends ReactiveCrudRepository<ProductEntity, UUID> {
    @Query("SELECT p.* FROM xt_product p JOIN xt_product_offering po ON p.uuid = po.product WHERE po.offering = :offering")
    Flux<ProductEntity> findAllByOffering(String offering, Pageable pageable);

    @Query("SELECT p.* FROM xt_product p JOIN xt_product_offering po ON p.uuid = po.product WHERE po.uuid = :productOfferingUuid")
    Mono<ProductEntity> findByProductOfferingUuid(UUID productOfferingUuid);

    @Query("SELECT COUNT(*) > 0 FROM xt_product_offering WHERE uuid = :productOfferingUuid")
    Mono<Boolean> existsByProductOfferingUuid(UUID productOfferingUuid);

    Flux<ProductEntity> findAllBy(Pageable pageable);

    @Query("SELECT COUNT(p.*) FROM xt_product p JOIN xt_product_offering po ON p.uuid = po.product WHERE po.offering = :offering")
    Mono<Long> countAllByOffering(String offering);
}
