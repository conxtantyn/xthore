package com.xthore.persistence.order.datasource;

import com.xthore.persistence.order.entity.CustomerEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public interface CustomerDatasource extends ReactiveCrudRepository<CustomerEntity, UUID> {
}
