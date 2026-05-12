package com.xthore.rest.catalog.controller;

import com.xthore.domain.catalog.model.Offering;
import com.xthore.domain.catalog.usecase.ListOfferingsUsecase;
import com.xthore.domain.catalog.usecase.RetrieveOfferingByProductUsecase;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/offering")
public class OfferingController {
    private final ListOfferingsUsecase listOfferingsUsecase;

    private final RetrieveOfferingByProductUsecase retrieveOfferingByProductUsecase;

    public OfferingController(
            ListOfferingsUsecase listOfferingsUsecase,
            RetrieveOfferingByProductUsecase retrieveOfferingByProductUsecase
    ) {
        this.listOfferingsUsecase = listOfferingsUsecase;
        this.retrieveOfferingByProductUsecase = retrieveOfferingByProductUsecase;
    }

    @ResponseBody
    @GetMapping
    Flux<Offering> findAll() {
        return listOfferingsUsecase.execute();
    }

    @ResponseBody
    @GetMapping("/{product}")
    Mono<Offering> findByProduct(@PathVariable String product) {
        return retrieveOfferingByProductUsecase.execute(product);
    }
}
