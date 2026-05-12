package com.xthore.persistence.catalog.datasource;

import com.xthore.persistence.catalog.entity.ProductEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductDatasource extends ReactiveCrudRepository<ProductEntity, String> {
}
