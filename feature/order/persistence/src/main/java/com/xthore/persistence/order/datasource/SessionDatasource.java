package com.xthore.persistence.order.datasource;

import com.xthore.persistence.order.entity.SessionEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SessionDatasource extends ReactiveCrudRepository<SessionEntity, String> {
}
