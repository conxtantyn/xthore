package com.xthore.persistence.order.delegate;

import com.xthore.data.order.persistence.SessionPersistence;
import com.xthore.domain.order.model.Session;
import com.xthore.persistence.order.datasource.SessionDatasource;
import com.xthore.persistence.order.entity.SessionEntity;
import com.xthore.persistence.order.mapper.SessionMapper;

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
        SessionEntity entity = SessionMapper.mapFromDomain(record);
        return datasource.save(entity).map(SessionMapper::mapToDomain);
    }

    @Override
    public Mono<Session> findByKey(String key) {
        return datasource.findById(key).map(SessionMapper::mapToDomain);
    }
}
