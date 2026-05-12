package com.xthore.persistence.catalog.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import java.math.BigDecimal;

@Table("xt_product")
public record ProductEntity(
    @Id
    String id,
    String name,
    BigDecimal price
) {}
