package com.xthore.domain.catalog.usecase;

import com.xthore.common.usecase.MonoWithArgsUsecase;
import com.xthore.domain.catalog.repository.OfferingRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class CheckOfferingAvailabilityUsecase implements MonoWithArgsUsecase<String, Boolean> {
    private final OfferingRepository repository;

    public CheckOfferingAvailabilityUsecase(OfferingRepository repository) {
        this.repository = repository;
    }

    @Override
    public Mono<Boolean> execute(String product) {
        return repository.isAvailable(product);
    }
}
