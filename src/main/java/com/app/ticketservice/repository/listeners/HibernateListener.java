package com.app.ticketservice.repository.listeners;

import lombok.RequiredArgsConstructor;
import org.hibernate.event.service.spi.EventListenerRegistry;
import org.hibernate.event.spi.EventType;
import org.hibernate.internal.SessionFactoryImpl;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManagerFactory;

@RequiredArgsConstructor
@Component
public class HibernateListener {

    private final EntityManagerFactory entityManagerFactory;

    @PostConstruct
    private void init(){
        SessionFactoryImpl sessionFactory = entityManagerFactory.unwrap(SessionFactoryImpl.class);
        EventListenerRegistry registry = sessionFactory.getServiceRegistry().getService(EventListenerRegistry.class);
        registry.getEventListenerGroup(EventType.POST_INSERT).appendListener(new TicketPostInsertEventListener());
        registry.getEventListenerGroup(EventType.PRE_INSERT).appendListener(new TicketPreInsertEventListener());
    }

}
