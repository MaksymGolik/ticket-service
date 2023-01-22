package com.app.ticketservice.mapper;

import com.app.ticketservice.dto.BusRouteResponse;
import com.app.ticketservice.model.BusRoute;
import com.app.ticketservice.dto.BusRouteCreateRequest;

public class BusRouteMapper {
    public static BusRouteResponse modelToResponseDto(BusRoute busRoute){
        return BusRouteResponse.builder()
                .id(busRoute.getId())
                .departure(busRoute.getDeparture())
                .arrival(busRoute.getArrival())
                .departureTime(busRoute.getDepartureTime())
                .ticketPrice(busRoute.getTicketPrice())
                .availableTickets(busRoute.getAvailableTickets())
                .build();
    }

    public static BusRoute createDtoToModel(BusRouteCreateRequest busRouteCreateRequest){
        return BusRoute.builder()
                .departure(busRouteCreateRequest.getDeparture())
                .arrival(busRouteCreateRequest.getArrival())
                .departureTime(busRouteCreateRequest.getDepartureTime())
                .ticketPrice(busRouteCreateRequest.getTicketPrice())
                .availableTickets(busRouteCreateRequest.getAvailableTickets())
                .build();
    }
}
