package com.xthore.common.usecase;

public interface BlockingUsecase<T> extends Usecase {
    T execute();
}
