package com.xthore.remote.order.client;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

import com.xthore.remote.order.model.Offering;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "catalog", primary = false)
public interface OfferingClient {
    @GetMapping(name = "/search/{id}/profile", produces = { APPLICATION_JSON_VALUE })
    Offering get(@PathVariable String id);
}

