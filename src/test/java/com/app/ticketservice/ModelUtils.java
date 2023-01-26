package com.app.ticketservice;

import com.app.ticketservice.dto.BusRouteCreateUpdateRequest;
import com.app.ticketservice.dto.BusRouteResponse;
import com.app.ticketservice.dto.TicketCreateRequest;
import com.app.ticketservice.model.BusRoute;
import com.app.ticketservice.model.Ticket;

import java.time.LocalDateTime;

public class ModelUtils {
    public static BusRouteResponse getBusRouteResponse(){
        return BusRouteResponse.builder()
                .id(1L)
                .departure("Kyiv")
                .arrival("Kharkiv")
                .departureTime(LocalDateTime.of(2023,6,15,14,25))
                .availableTickets(75)
                .ticketPrice(499.99)
                .build();
    }

    public static BusRoute getBusRoute(){
        return BusRoute
                .builder()
                .id(1L)
                .departure("Kyiv")
                .arrival("Kharkiv")
                .departureTime(LocalDateTime.of(2023,6,15,14,25))
                .availableTickets(75)
                .ticketPrice(499.99)
                .build();
    }

    public static BusRouteCreateUpdateRequest getBusRouteCreateUpdateRequest(){
        return BusRouteCreateUpdateRequest
                .builder()
                .departure("Kyiv")
                .arrival("Kharkiv")
                .departureTime(LocalDateTime.of(2023,6,15,14,25))
                .availableTickets(75)
                .ticketPrice(499.99)
                .build();
    }

    public static TicketCreateRequest getTicketCreateRequest(){
        return TicketCreateRequest
                .builder()
                .firstName("Петро")
                .lastName("Іванов")
                .middleName("Володимирович")
                .busRoute(1L)
                .build();
    }

    public static Ticket getTicket(){
        return Ticket
                .builder()
                .id(1L)
                .firstName("Петро")
                .lastName("Іванов")
                .middleName("Володимирович")
                .busRoute(getBusRoute())
                .build();
    }
}