package com.app.ticketservice.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Builder
@Data
public class BusRouteResponse {
    private Long id;
    private String departure;
    private String arrival;
    private LocalDateTime departureTime;
    private double ticketPrice;
    private int availableTickets;
}
