package com.xthore.data.order.persistence;

import com.xthore.domain.order.model.Session;
import reactor.core.publisher.Mono;

public interface SessionPersistence {
    Mono<Session> save(Session record);

    Mono<Session> findByKey(String key);
}
