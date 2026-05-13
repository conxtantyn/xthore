package com.xthore.remote.order.client;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

import com.xthore.remote.order.model.Offering;
import com.xthore.remote.model.RemoteResponse;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "catalog", primary = false)
public interface OfferingClient {
    @GetMapping(value = "/offering/{product}", produces = { APPLICATION_JSON_VALUE })
    RemoteResponse<Offering> get(@PathVariable("product") String product);
}
