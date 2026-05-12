package com.xthore.persistence.order.mapper;

import com.xthore.domain.order.model.Session;
import com.xthore.persistence.order.entity.SessionEntity;

public class SessionMapper {
    public static SessionEntity mapFromDomain(Session session) {
        return new SessionEntity(
                session.key(),
                session.request(),
                session.order(),
                session.createdAt()
        );
    }

    public static Session mapToDomain(SessionEntity session) {
        return new Session(
            session.key(),
            session.requestHash(),
            session.orderUuid(),
            session.createdAt()
        );
    }
}

