package com.app.ticketservice.mapper;

import com.app.ticketservice.dto.TicketCreateRequest;
import com.app.ticketservice.dto.TicketResponse;
import com.app.ticketservice.model.BusRoute;
import com.app.ticketservice.model.Ticket;

public class TicketMapper {
    public static Ticket createDtoToModel(TicketCreateRequest ticketCreateRequest){
        return Ticket.builder()
                .firstName(ticketCreateRequest.getFirstName())
                .middleName(ticketCreateRequest.getMiddleName())
                .lastName(ticketCreateRequest.getLastName())
                .busRoute(BusRoute.builder().id(ticketCreateRequest.getBusRoute()).build())
                .build();
    }

    public static TicketResponse modelToResponseDto(Ticket ticket){
        return TicketResponse.builder()
                .id(ticket.getId())
                .firstName(ticket.getFirstName())
                .middleName(ticket.getMiddleName())
                .lastName(ticket.getLastName())
                .busRoute(BusRouteMapper.modelToResponse(ticket.getBusRoute()))
                .build();
    }
}
