package com.xthore.domain.order.interactor;

import com.xthore.domain.order.model.Session;

import java.util.UUID;

import reactor.core.publisher.Mono;

public interface SessionInteractor {
    Mono<Session> check(String key, String request);

    Mono<Session> create(String key, String request, UUID order);
}

