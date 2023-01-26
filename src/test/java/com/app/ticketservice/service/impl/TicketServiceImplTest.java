package com.app.ticketservice.service.impl;

import com.app.ticketservice.ModelUtils;
import com.app.ticketservice.dto.PaymentCreateRequest;
import com.app.ticketservice.dto.PaymentResponse;
import com.app.ticketservice.dto.TicketCreateRequest;
import com.app.ticketservice.model.Ticket;
import com.app.ticketservice.repository.BusRouteRepository;
import com.app.ticketservice.repository.TicketRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class TicketServiceImplTest {
    @Mock
    private TicketRepository ticketRepository;
    @Mock
    private BusRouteRepository busRouteRepository;
    @Mock
    private RestTemplate restTemplate;
    private RestTemplateBuilder restTemplateBuilder = new RestTemplateBuilder();
    @InjectMocks
    private TicketServiceImpl ticketService = new TicketServiceImpl("",restTemplateBuilder,ticketRepository,busRouteRepository);

    @Test
    void save() {
        TicketCreateRequest ticketCreate = ModelUtils.getTicketCreateRequest();
        doReturn(Optional.of(ModelUtils.getBusRoute())).when(busRouteRepository).findById(ticketCreate.getBusRoute());
        doReturn(ModelUtils.getTicket()).when(ticketRepository).save(any(Ticket.class));
        ticketService.save(ticketCreate);
        verify(busRouteRepository,times(1)).findById(ticketCreate.getBusRoute());
        verify(ticketRepository, times(1)).save(any(Ticket.class));
        verify(restTemplate, times(1)).postForObject(eq("/payments/makePayment"),any(PaymentCreateRequest.class), eq(PaymentResponse.class));
    }
}