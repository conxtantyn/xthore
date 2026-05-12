package com.xthore.domain.catalog.usecase;

import com.xthore.common.model.Paging;
import com.xthore.common.usecase.PagerWithArgsUsecase;
import com.xthore.domain.catalog.model.Product;
import com.xthore.domain.catalog.repository.ProductRepository;
import org.springframework.stereotype.Service;

@Service
public class ListProductsUsecase implements PagerWithArgsUsecase<ListProductsUsecase.Args, Product> {
    private final ProductRepository repository;

    public ListProductsUsecase(ProductRepository repository) {
        this.repository = repository;
    }

    @Override
    public Paging.Response<Product> execute(Args args) {
        if (args.offering() != null) {
            return repository.findAllByOffering(args.offering(), args.page());
        }
        return repository.findAll(args.page());
    }

    public record Args(String offering, Paging.Page page) {}
}
