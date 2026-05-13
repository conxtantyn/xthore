package com.xthore.data.order.api;

import reactor.core.publisher.Mono;

public interface OfferingApi {
    Mono<Boolean> isAvailable(String product);
}
