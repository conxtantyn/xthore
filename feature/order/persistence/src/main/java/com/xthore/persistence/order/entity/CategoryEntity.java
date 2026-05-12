package com.xthore.persistence.order.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("xt_category")
public record CategoryEntity(
    @Id
    String name,
    String description,
    @Column("created_at")
    Long createdAt
) {}
