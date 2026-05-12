package com.xthore.persistence.order.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import java.util.UUID;

@Table("xt_order")
public record OrderEntity(
    @Id
    UUID uuid,
    String state,
    String category,
    UUID customer,
    @Column("site_id")
    String siteId,
    @Column("created_at")
    Long createdAt,
    @Column("updated_at")
    Long updatedAt
) {}
