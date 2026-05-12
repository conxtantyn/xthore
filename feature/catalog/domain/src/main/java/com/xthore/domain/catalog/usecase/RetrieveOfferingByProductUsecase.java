package com.xthore.domain.catalog.usecase;

import com.xthore.common.usecase.MonoWithArgsUsecase;
import com.xthore.domain.catalog.model.Offering;
import com.xthore.domain.catalog.repository.OfferingRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class RetrieveOfferingByProductUsecase implements MonoWithArgsUsecase<String, Offering> {
    private final OfferingRepository repository;

    public RetrieveOfferingByProductUsecase(OfferingRepository repository) {
        this.repository = repository;
    }

    @Override
    public Mono<Offering> execute(String product) {
        return repository.findByProduct(product);
    }
}
