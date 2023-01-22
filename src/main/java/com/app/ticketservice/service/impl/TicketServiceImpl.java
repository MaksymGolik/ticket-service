package com.app.ticketservice.service.impl;

import com.app.ticketservice.dto.TicketCreateRequest;
import com.app.ticketservice.dto.TicketResponse;
import com.app.ticketservice.mapper.TicketMapper;
import com.app.ticketservice.repository.TicketRepository;
import com.app.ticketservice.service.TicketService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TicketServiceImpl implements TicketService {

    private TicketRepository ticketRepository;

    @Override
    public synchronized TicketResponse save(TicketCreateRequest ticketCreateRequest) {
        return TicketMapper.modelToResponseDto(ticketRepository
                .save(TicketMapper.createDtoToModel(ticketCreateRequest)));
    }

    @Override
    public TicketResponse findTicketById(Long id) {
        return TicketMapper.modelToResponseDto(ticketRepository
                .findById(id).orElseThrow(()-> new IllegalArgumentException("No ticket found by id "+ id)));
    }
}
