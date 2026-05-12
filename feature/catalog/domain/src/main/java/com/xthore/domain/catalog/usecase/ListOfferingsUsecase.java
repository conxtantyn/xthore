package com.xthore.domain.catalog.usecase;

import com.xthore.common.usecase.FluxUsecase;
import com.xthore.domain.catalog.model.Offering;
import com.xthore.domain.catalog.repository.OfferingRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class ListOfferingsUsecase implements FluxUsecase<Offering> {
    private final OfferingRepository repository;

    public ListOfferingsUsecase(OfferingRepository repository) {
        this.repository = repository;
    }

    @Override
    public Flux<Offering> execute() {
        return repository.findAll();
    }
}
