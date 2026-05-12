package com.xthore.persistence.catalog.mapper;

import com.xthore.domain.catalog.model.Offering;
import com.xthore.persistence.catalog.entity.OfferingEntity;

public class OfferingMapper {
    public static Offering mapToDomain(OfferingEntity offering) {
        return new Offering(offering.name(), offering.description(), offering.createdAt());
    }
}
