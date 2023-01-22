package com.app.ticketservice.repository.listeners;

import com.app.ticketservice.model.Ticket;
import org.hibernate.FlushMode;
import org.hibernate.event.spi.PostInsertEvent;
import org.hibernate.event.spi.PostInsertEventListener;
import org.hibernate.persister.entity.EntityPersister;
import org.springframework.stereotype.Component;

@Component
public class TicketPostInsertEventListener implements PostInsertEventListener {
    @Override
    public void onPostInsert(PostInsertEvent postInsertEvent) {
        if(postInsertEvent.getEntity() instanceof final Ticket ticket) {
            postInsertEvent.getSession().createNativeQuery("UPDATE bus_routes SET available_tickets=available_tickets-1 WHERE id=:id")
                    .setParameter("id", ticket.getBusRoute().getId())
                    .setFlushMode(FlushMode.MANUAL)
                    .executeUpdate();
        }
    }

    @Override
    public boolean requiresPostCommitHanding(EntityPersister entityPersister) {
        return false;
    }
}
