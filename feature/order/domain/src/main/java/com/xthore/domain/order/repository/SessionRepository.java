package com.xthore.domain.order.repository;

import com.xthore.domain.order.model.Session;
import reactor.core.publisher.Mono;

public interface SessionRepository {
    Mono<Session> save(Session record);

    Mono<Session> findByKey(String key);
}
