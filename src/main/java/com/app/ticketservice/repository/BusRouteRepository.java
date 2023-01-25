package com.app.ticketservice.repository;

import com.app.ticketservice.model.BusRoute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BusRouteRepository extends JpaRepository<BusRoute, Long> {
    @Modifying
    @Query(nativeQuery = true, value = "UPDATE bus_routes SET available_tickets=available_tickets+:ticketAmount WHERE id=:busRouteId")
    void refundCancelledTickets(@Param("busRouteId") Long busRouteId, @Param("ticketAmount") Long ticketAmount);
}
