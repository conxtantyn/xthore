package com.xthore.persistence.catalog.datasource;

import com.xthore.persistence.catalog.entity.ProductOfferingEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ProductOfferingDatasource extends ReactiveCrudRepository<ProductOfferingEntity, UUID> {
}
