package com.app.ticketservice.model;

import javax.persistence.*;

import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "bus_routes")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class BusRoute {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "departure", nullable = false)
    private String departure;
    @Column(name = "arrival", nullable = false)
    private String arrival;
    @Column(name = "departure_time", nullable = false)
    private LocalDateTime departureTime;
    @Column(name="ticket_price", nullable = false)
    private double ticketPrice;
    @Column(name="available_tickets", nullable = false)
    private int availableTickets;
}
