package com.app.ticketservice.repository;

import com.app.ticketservice.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket,Long> {
    @Query(value = "SELECT ticket FROM Ticket ticket WHERE ticket.id IN :ticketIdList")
    List<Ticket> getAllById(@Param("ticketIdList") List<Long> ticketIdList);
}
