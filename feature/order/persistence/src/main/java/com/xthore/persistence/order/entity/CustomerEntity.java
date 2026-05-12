package com.xthore.persistence.order.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import java.util.UUID;

@Table("xt_customer")
public record CustomerEntity(
    @Id
    UUID uuid,
    @Column("payment_method")
    String paymentMethod,
    String iban,
    @Column("created_at")
    Long createdAt
) {}
