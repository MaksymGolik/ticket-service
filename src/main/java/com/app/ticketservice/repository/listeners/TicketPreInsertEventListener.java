package com.app.ticketservice.repository.listeners;

import com.app.ticketservice.model.Ticket;
import org.hibernate.FlushMode;
import org.hibernate.event.spi.PreInsertEvent;
import org.hibernate.event.spi.PreInsertEventListener;

public class TicketPreInsertEventListener implements PreInsertEventListener {
    @Override
    public boolean onPreInsert(PreInsertEvent preInsertEvent) {
        if(preInsertEvent.getEntity() instanceof final Ticket ticket){
            int ticketsAmount = (int)
                    preInsertEvent.getSession()
                    .createNativeQuery("SELECT available_tickets FROM bus_routes WHERE id=:id")
                    .setParameter("id", ticket.getBusRoute().getId())
                    .setFlushMode(FlushMode.MANUAL)
                    .getSingleResult();

            if(ticketsAmount==0)throw new IllegalStateException("No available tickets for chosen bus route");
        }
        return false;
    }
}
