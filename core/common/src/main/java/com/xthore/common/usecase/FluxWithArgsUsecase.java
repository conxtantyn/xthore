package com.xthore.common.usecase;

import reactor.core.publisher.Flux;

public interface FluxWithArgsUsecase<A, T> extends Usecase {
    Flux<T> execute(A args);
}
