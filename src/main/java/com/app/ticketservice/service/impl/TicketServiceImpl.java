package com.app.ticketservice.service.impl;

import com.app.ticketservice.dto.PaymentCreateRequest;
import com.app.ticketservice.dto.PaymentResponse;
import com.app.ticketservice.dto.TicketCreateRequest;
import com.app.ticketservice.dto.TicketResponse;
import com.app.ticketservice.mapper.TicketMapper;
import com.app.ticketservice.model.BusRoute;
import com.app.ticketservice.model.Ticket;
import com.app.ticketservice.repository.BusRouteRepository;
import com.app.ticketservice.repository.TicketRepository;
import com.app.ticketservice.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.atomic.AtomicReference;

@Service
public class TicketServiceImpl implements TicketService {

    private TicketRepository ticketRepository;
    private BusRouteRepository busRouteRepository;
    private RestTemplate restTemplate;

    @Autowired
    public TicketServiceImpl (@Value("${paymentservice.base.url}") String baseUrl,
                              RestTemplateBuilder restTemplateBuilder,
                              TicketRepository ticketRepository, BusRouteRepository busRouteRepository){
        restTemplate = restTemplateBuilder.rootUri(baseUrl).build();
        this.ticketRepository = ticketRepository;
        this.busRouteRepository = busRouteRepository;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.SERIALIZABLE)
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
        Ticket ticket = ticketRepository.save(TicketMapper.createDtoToModel(ticketCreateRequest));
        PaymentResponse paymentResponse =
                restTemplate.postForObject("/payments/makePayment", makePaymentCreateRequest(ticket, busRoute.get()), PaymentResponse.class);
        return addPaymentStatusToTicketResponse(TicketMapper.modelToResponseDto(ticket), paymentResponse);
    }

    @Override
    public TicketResponse findTicketById(Long id) {
        TicketResponse ticketResponse = TicketMapper.modelToResponseDto(ticketRepository
                .findById(id).orElseThrow(()-> new IllegalArgumentException("No ticket found by id "+ id)));
        PaymentResponse ticketPayment = restTemplate.getForObject("/payments/{ticketId}",PaymentResponse.class,id);
        return addPaymentStatusToTicketResponse(ticketResponse, ticketPayment);
    }

    private PaymentCreateRequest makePaymentCreateRequest(Ticket ticket, BusRoute busRoute){
        return PaymentCreateRequest.builder()
                .firstName(ticket.getFirstName())
                .middleName(ticket.getMiddleName())
                .lastName(ticket.getLastName())
                .paymentAmount(busRoute.getTicketPrice())
                .ticketId(ticket.getId())
                .build();
    }

    private TicketResponse addPaymentStatusToTicketResponse(TicketResponse ticketResponse,
                                                            PaymentResponse paymentResponse){
        if(paymentResponse!=null) ticketResponse.setPaymentStatus(paymentResponse.getStatus());
        return ticketResponse;
    }}
