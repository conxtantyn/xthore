package com.xthore.common.usecase;

import reactor.core.publisher.Flux;

public interface FluxUsecase<T> extends Usecase {
    Flux<T> execute();
}
