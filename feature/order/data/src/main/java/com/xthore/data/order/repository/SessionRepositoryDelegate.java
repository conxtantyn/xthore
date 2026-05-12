package com.xthore.data.order.repository;

import com.xthore.data.order.persistence.SessionPersistence;
import com.xthore.domain.order.model.Session;
import com.xthore.domain.order.repository.SessionRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public class SessionRepositoryDelegate implements SessionRepository {
    private final SessionPersistence persistence;

    public SessionRepositoryDelegate(SessionPersistence persistence) {
        this.persistence = persistence;
    }

    @Override
    public Mono<Session> save(Session record) {
        return persistence.save(record);
    }

    @Override
    public Mono<Session> findByKey(String key) {
        return persistence.findByKey(key);
    }
}
