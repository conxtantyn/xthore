package com.xthore.common.usecase;

public interface BlockingWithArgsUsecase<A, T> extends Usecase {
    T execute(A args);
}
