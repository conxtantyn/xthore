package com.xthore.persistence.catalog.datasource;

import com.xthore.persistence.catalog.entity.OfferingEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;
import java.util.UUID;

@Repository
public interface OfferingDatasource extends ReactiveCrudRepository<OfferingEntity, String> {
    @Query("SELECT o.* FROM xt_offering o JOIN xt_product_offering po ON o.name = po.offering WHERE po.product = :productUuid")
    Mono<OfferingEntity> findByProductUuid(UUID productUuid);
}
