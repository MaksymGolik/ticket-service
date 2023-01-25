package com.app.ticketservice.dto;

import lombok.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BusRouteCreateUpdateRequest {
    @NotEmpty
    private String departure;
    @NotEmpty
    private String arrival;
    @NotNull
    private LocalDateTime departureTime;
    @NotNull
    @Positive
    private double ticketPrice;
    @NotNull
    @Min(0)
    private int availableTickets;
}
