package com.app.ticketservice.mapper;

import com.app.ticketservice.dto.BusRouteResponse;
import com.app.ticketservice.model.BusRoute;
import com.app.ticketservice.dto.BusRouteCreateUpdateRequest;

public class BusRouteMapper {
    public static BusRouteResponse modelToResponse(BusRoute busRoute){
        return BusRouteResponse.builder()
                .id(busRoute.getId())
                .departure(busRoute.getDeparture())
                .arrival(busRoute.getArrival())
                .departureTime(busRoute.getDepartureTime())
                .ticketPrice(busRoute.getTicketPrice())
                .availableTickets(busRoute.getAvailableTickets())
                .build();
    }

    public static BusRoute createUpdateDtoToModel(BusRouteCreateUpdateRequest busRouteCreateUpdateRequest){
        return BusRoute.builder()
                .departure(busRouteCreateUpdateRequest.getDeparture())
                .arrival(busRouteCreateUpdateRequest.getArrival())
                .departureTime(busRouteCreateUpdateRequest.getDepartureTime())
                .ticketPrice(busRouteCreateUpdateRequest.getTicketPrice())
                .availableTickets(busRouteCreateUpdateRequest.getAvailableTickets())
                .build();
    }
}
