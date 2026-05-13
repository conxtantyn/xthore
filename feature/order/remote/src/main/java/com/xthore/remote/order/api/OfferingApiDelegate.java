package com.xthore.remote.order.api;

import com.xthore.data.order.api.OfferingApi;
import com.xthore.remote.order.client.OfferingClient;
import com.xthore.remote.order.model.Offering;
import feign.FeignException;

import org.springframework.stereotype.Service;

@Service
public class OfferingApiDelegate implements OfferingApi {
    private final OfferingClient client;

    public OfferingApiDelegate(OfferingClient client) {
        this.client = client;
    }

    @Override
    public Boolean isAvailable(String product) {
        try {
            Offering offering = client.get(product);
            return offering != null && offering.name() != null;
        } catch (FeignException.NotFound e) {
            return false;
        } catch (Exception e) {
            return false;
        }
    }
}
