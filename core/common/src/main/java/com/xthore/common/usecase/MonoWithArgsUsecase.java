package com.xthore.common.usecase;

import reactor.core.publisher.Mono;

public interface MonoWithArgsUsecase<A, T> extends Usecase {
    Mono<T> execute(A args);
}
