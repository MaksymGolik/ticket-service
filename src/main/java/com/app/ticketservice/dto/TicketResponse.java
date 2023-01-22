package com.app.ticketservice.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class TicketResponse {
    private Long id;
    private String firstName;
    private String middleName;
    private String lastName;
    private BusRouteResponse busRoute;
}
