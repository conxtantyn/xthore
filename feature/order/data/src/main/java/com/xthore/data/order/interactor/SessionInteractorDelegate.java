package com.xthore.data.order.interactor;

import com.xthore.domain.order.exception.SessionConflictException;
import com.xthore.domain.order.interactor.SessionInteractor;
import com.xthore.domain.order.model.Session;
import com.xthore.domain.order.repository.SessionRepository;

import org.springframework.stereotype.Service;

import java.util.UUID;

import reactor.core.publisher.Mono;

@Service
public class SessionInteractorDelegate implements SessionInteractor {
    private final SessionRepository repository;

    public SessionInteractorDelegate(SessionRepository repository) {
        this.repository = repository;
    }

    @Override
    public Mono<Session> check(String key, String request) {
        if (key == null) return Mono.empty();
        return repository.findByKey(key)
                .flatMap(record -> {
                    if (!record.request().equals(request)) {
                        return Mono.error(new SessionConflictException());
                    }
                    return Mono.just(record);
                });
    }

    @Override
    public Mono<Session> create(String key, String request, UUID order) {
        if (key == null) return Mono.empty();
        return repository.save(new Session(key, request, order, System.currentTimeMillis()));
    }
}

