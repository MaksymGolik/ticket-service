package com.app.ticketservice.service.impl;

import com.app.ticketservice.dto.TicketCreateRequest;
import com.app.ticketservice.dto.TicketResponse;
import com.app.ticketservice.mapper.TicketMapper;
import com.app.ticketservice.model.BusRoute;
import com.app.ticketservice.repository.BusRouteRepository;
import com.app.ticketservice.repository.TicketRepository;
import com.app.ticketservice.service.TicketService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicReference;

@Service
@AllArgsConstructor
public class TicketServiceImpl implements TicketService {

    private TicketRepository ticketRepository;
    private BusRouteRepository busRouteRepository;

    @Override
    public TicketResponse save(TicketCreateRequest ticketCreateRequest) {
        AtomicReference<BusRoute> busRoute = new AtomicReference<>();
        busRouteRepository.findById(ticketCreateRequest.getBusRoute())
                .ifPresentOrElse(busRoute::set,()->{throw new IllegalArgumentException("No bus route found by id " + ticketCreateRequest.getBusRoute());});
        int availableTickets = busRoute.get().getAvailableTickets();
        if(availableTickets==0){
            throw new IllegalStateException("No available tickets for chosen bus route");
        }
        busRoute.get().setAvailableTickets(--availableTickets);
        busRouteRepository.save(busRoute.get());
        return TicketMapper.modelToResponseDto(ticketRepository
                .save(TicketMapper.createDtoToModel(ticketCreateRequest)));
    }

    @Override
    public TicketResponse findTicketById(Long id) {
        return TicketMapper.modelToResponseDto(ticketRepository
                .findById(id).orElseThrow(()-> new IllegalArgumentException("No ticket found by id "+ id)));
    }
}
