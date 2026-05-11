package com.xthore.common.usecase;

import com.xthore.common.model.Paging;

public interface PagerWithArgsUsecase<A, T> extends Usecase {
    Paging.Response<T> execute(A args);
}
