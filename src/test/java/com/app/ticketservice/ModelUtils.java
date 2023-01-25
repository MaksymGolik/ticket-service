package com.app.ticketservice;

import com.app.ticketservice.dto.BusRouteCreateUpdateRequest;
import com.app.ticketservice.dto.BusRouteResponse;

import java.time.LocalDateTime;

public class ModelUtils {
    public static BusRouteResponse getBusRouteResponse(){
        return BusRouteResponse.builder()
                .id(1L)
                .departure("Kyiv")
                .arrival("Kharkiv")
                .departureTime(LocalDateTime.of(2023,1,25,14,15))
                .availableTickets(75)
                .ticketPrice(499.99)
                .build();
    }

    public static BusRouteCreateUpdateRequest getBusRouteCreateUpdateRequest(){
        return BusRouteCreateUpdateRequest
                .builder()
                .departure("Kyiv")
                .arrival("Kharkiv")
                .departureTime(LocalDateTime.now())
                .availableTickets(75)
                .ticketPrice(499.99)
                .build();
    }
}
