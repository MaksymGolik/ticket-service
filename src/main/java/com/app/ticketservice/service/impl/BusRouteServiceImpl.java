package com.app.ticketservice.service.impl;

import com.app.ticketservice.dto.BusRouteResponse;
import com.app.ticketservice.mapper.BusRouteMapper;
import com.app.ticketservice.dto.BusRouteCreateUpdateRequest;
import com.app.ticketservice.model.BusRoute;
import com.app.ticketservice.model.Ticket;
import com.app.ticketservice.repository.BusRouteRepository;
import com.app.ticketservice.repository.TicketRepository;
import com.app.ticketservice.service.BusRouteService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class BusRouteServiceImpl implements BusRouteService {

    private BusRouteRepository busRouteRepository;
    private TicketRepository ticketRepository;

    @Override
    public List<BusRouteResponse> findAll(Pageable page) {
        return busRouteRepository.findAll(page)
                .stream()
                .map(BusRouteMapper::modelToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public BusRouteResponse save(BusRouteCreateUpdateRequest busRouteCreateUpdateRequest) {
        if(LocalDateTime.now().isBefore(busRouteCreateUpdateRequest.getDepartureTime()))
            throw new IllegalArgumentException("Cannot add bus route in the past");
        return BusRouteMapper.modelToResponse(
                busRouteRepository.save(
                        BusRouteMapper.createUpdateDtoToModel(busRouteCreateUpdateRequest)));
    }

    @Override
    public BusRouteResponse update(Long id, BusRouteCreateUpdateRequest busRouteCreateUpdateRequest) {
        AtomicReference<Long> updatedId = new AtomicReference<>();
        busRouteRepository.findById(id).ifPresentOrElse(
                (busRoute) -> updatedId.set(busRoute.getId()),
                ()-> {
                    throw new IllegalArgumentException("No bus route found by id " + id);
                }
        );
        BusRoute busRoute = BusRouteMapper.createUpdateDtoToModel(busRouteCreateUpdateRequest);
        busRoute.setId(updatedId.get());
        return BusRouteMapper.modelToResponse(busRouteRepository.save(busRoute));
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.SERIALIZABLE)
    @Override
    public int cancelTicketOccupation(List<Long> failedTicketsId) {
        AtomicInteger affectedRows = new AtomicInteger(0);
        Map<BusRoute, Long> failedTickets = ticketRepository
                .findAllById(failedTicketsId)
                .stream()
                .collect(Collectors.groupingBy(Ticket::getBusRoute,Collectors.counting()));
        failedTickets.forEach((key, value) -> affectedRows.addAndGet(busRouteRepository.refundCancelledTickets(key.getId(), value)));
        return affectedRows.get();
    }
}