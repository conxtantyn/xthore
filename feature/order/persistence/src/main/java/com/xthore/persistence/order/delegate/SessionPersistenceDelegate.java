package com.xthore.persistence.order.delegate;

import com.xthore.data.order.persistence.SessionPersistence;
import com.xthore.domain.order.model.Session;
import com.xthore.persistence.order.datasource.SessionDatasource;
import com.xthore.persistence.order.entity.SessionEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class SessionPersistenceDelegate implements SessionPersistence {
    private final SessionDatasource datasource;

    public SessionPersistenceDelegate(SessionDatasource datasource) {
        this.datasource = datasource;
    }

    @Override
    public Mono<Session> save(Session record) {
        SessionEntity entity = new SessionEntity(
            record.key(),
            record.request(),
            record.order(),
            record.createdAt()
        );
        return datasource.save(entity)
            .map(e -> new Session(e.key(), e.requestHash(), e.orderId(), e.createdAt()));
    }

    @Override
    public Mono<Session> findByKey(String key) {
        return datasource.findById(key)
            .map(e -> new Session(e.key(), e.requestHash(), e.orderId(), e.createdAt()));
    }
}
