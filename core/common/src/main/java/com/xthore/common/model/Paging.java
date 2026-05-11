package com.xthore.common.model;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public sealed interface Paging {
    record Page(int index, int size) implements Paging {}
    record Pageable(int index, int size, int count, long total) implements Paging {}
    record Response<T>(Mono<Pageable> pageable, Flux<T> items) implements Paging {}
}
