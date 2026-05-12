package com.xthore.persistence.order.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import java.util.UUID;

@Table("xt_article")
public class ArticleEntity implements Persistable<UUID> {
    @Id
    private final UUID uuid;
    @Column("order_uuid")
    private final UUID orderUuid;
    private final String offering;
    private final Integer quantity;

    @Transient
    private boolean isNew = true;

    public ArticleEntity(UUID uuid, UUID orderUuid, String offering, Integer quantity) {
        this.uuid = uuid;
        this.orderUuid = orderUuid;
        this.offering = offering;
        this.quantity = quantity;
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
    public UUID orderUuid() { return orderUuid; }
    public String offering() { return offering; }
    public Integer quantity() { return quantity; }
}
