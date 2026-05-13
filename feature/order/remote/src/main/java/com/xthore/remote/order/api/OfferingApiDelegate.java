package com.xthore.remote.order.api;

import com.xthore.data.order.api.OfferingApi;
import com.xthore.remote.model.RemoteResponse;
import com.xthore.remote.order.client.OfferingClient;
import com.xthore.remote.order.model.Offering;

import org.springframework.stereotype.Service;

import feign.FeignException;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Service
public class OfferingApiDelegate implements OfferingApi {
    private final OfferingClient client;

    public OfferingApiDelegate(OfferingClient client) {
        this.client = client;
    }

    @Override
    public Mono<Boolean> isAvailable(String product) {
        return Mono.fromCallable(() -> {
            RemoteResponse<Offering> response = client.get(product);
            return response != null && response.data() != null && response.data().name() != null;
        }).subscribeOn(Schedulers.boundedElastic())
                .onErrorResume(e -> {
                    if (e instanceof FeignException.NotFound) {
                        return Mono.just(false);
                    }
                    return Mono.error(e);
                });
    }
}
