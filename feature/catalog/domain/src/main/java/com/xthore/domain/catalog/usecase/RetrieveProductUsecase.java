package com.xthore.domain.catalog.usecase;

import com.xthore.common.usecase.MonoWithArgsUsecase;
import com.xthore.domain.catalog.model.Product;
import com.xthore.domain.catalog.repository.ProductRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class RetrieveProductUsecase implements MonoWithArgsUsecase<String, Product> {
    private final ProductRepository repository;

    public RetrieveProductUsecase(ProductRepository repository) {
        this.repository = repository;
    }

    @Override
    public Mono<Product> execute(String uuid) {
        return repository.find(uuid);
    }
}
