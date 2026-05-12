package com.xthore.persistence.order.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import java.util.UUID;

@Table("xt_order")
public class OrderEntity implements Persistable<UUID> {
    @Id
    private final UUID uuid;
    private final String state;
    private final String category;
    private final UUID customer;
    @Column("site_id")
    private final String siteId;
    @Column("created_at")
    private final Long createdAt;
    @Column("updated_at")
    private final Long updatedAt;

    @Transient
    private boolean isNew = true;

    public OrderEntity(
            UUID uuid,
            String state,
            String category,
            UUID customer,
            String siteId,
            Long createdAt,
            Long updatedAt
    ) {
        this.uuid = uuid;
        this.state = state;
        this.category = category;
        this.customer = customer;
        this.siteId = siteId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    @Override
    public UUID getId() {
        return uuid;
    }

    @Override
    public boolean isNew() {
        return isNew;
    }

    public void setNew(boolean isNew) {
        this.isNew = isNew;
    }

    public UUID uuid() { return uuid; }
    public String state() { return state; }
    public String category() { return category; }
    public UUID customer() { return customer; }
    public String siteId() { return siteId; }
    public Long createdAt() { return createdAt; }
    public Long updatedAt() { return updatedAt; }
}
