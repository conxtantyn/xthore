package com.xthore.common.usecase;

import reactor.core.publisher.Mono;

public interface MonoUsecase<T> extends Usecase {
    Mono<T> execute();
}
