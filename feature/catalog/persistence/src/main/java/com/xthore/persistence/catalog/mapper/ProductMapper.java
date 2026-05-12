package com.xthore.persistence.catalog.mapper;

import com.xthore.domain.catalog.model.Product;
import com.xthore.persistence.catalog.entity.ProductEntity;

public class ProductMapper {
    public static Product mapToDomain(ProductEntity product) {
        return new Product(product.uuid().toString(), product.name(), product.price().doubleValue());
    }
}
