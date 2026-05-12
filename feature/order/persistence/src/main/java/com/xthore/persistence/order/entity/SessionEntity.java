package com.xthore.persistence.order.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import java.util.UUID;

@Table("xt_session")
public class SessionEntity implements Persistable<String> {
    @Id
    private final String key;
    @Column("request")
    private final String request;
    @Column("order_uuid")
    private final UUID order;
    @Column("created_at")
    private final Long createdAt;

    @Transient
    private boolean isNew = true;

    public SessionEntity(String key, String request, UUID order, Long createdAt) {
        this.key = key;
        this.request = request;
        this.order = order;
        this.createdAt = createdAt;
    }

    @Override
    public String getId() {
        return key;
    }

    @Override
    public boolean isNew() {
        return isNew;
    }

    public void setNew(boolean isNew) {
        this.isNew = isNew;
    }

    public String key() { return key; }
    public String requestHash() { return request; }
    public UUID orderUuid() { return order; }
    public Long createdAt() { return createdAt; }
}
