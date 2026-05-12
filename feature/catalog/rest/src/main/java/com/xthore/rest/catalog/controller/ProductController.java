package com.xthore.rest.catalog.controller;

import com.xthore.common.model.Paging;
import com.xthore.domain.catalog.model.Product;
import com.xthore.domain.catalog.usecase.ListProductsUsecase;
import com.xthore.domain.catalog.usecase.RetrieveProductUsecase;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/product")
public class ProductController {
    private final RetrieveProductUsecase retrieveProductUsecase;
    private final ListProductsUsecase listProductsUsecase;

    public ProductController(
            RetrieveProductUsecase retrieveProductUsecase,
            ListProductsUsecase listProductsUsecase
    ) {
        this.retrieveProductUsecase = retrieveProductUsecase;
        this.listProductsUsecase = listProductsUsecase;
    }

    @ResponseBody
    @GetMapping("/{uuid}")
    public Mono<Product> find(@PathVariable String uuid) {
        return retrieveProductUsecase.execute(uuid)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found")));
    }

    @ResponseBody
    @GetMapping
    public Mono<Map<String, Object>> findAll(
            @RequestParam(required = false) String offering,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Paging.Response<Product> response = listProductsUsecase.execute(new ListProductsUsecase
                .Args(offering, new Paging.Page(page, size)));
        
        return Mono.zip(response.pageable(), response.items().collectList())
                .map(tuple -> Map.of(
                        "pageable", tuple.getT1(),
                        "items", tuple.getT2()
                ));
    }
}
