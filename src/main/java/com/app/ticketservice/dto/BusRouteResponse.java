package com.app.ticketservice.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class BusRouteResponse {
    private Long id;
    private String departure;
    private String arrival;
    private LocalDateTime departureTime;
    private double ticketPrice;
    private int availableTickets;
}
