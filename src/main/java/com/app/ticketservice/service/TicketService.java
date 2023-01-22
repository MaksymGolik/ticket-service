package com.app.ticketservice.service;

import com.app.ticketservice.dto.TicketCreateRequest;
import com.app.ticketservice.dto.TicketResponse;

public interface TicketService {
    TicketResponse save(TicketCreateRequest ticketCreateRequest);
    TicketResponse findTicketById(Long id);
}
