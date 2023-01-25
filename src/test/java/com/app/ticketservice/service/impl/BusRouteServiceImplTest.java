package com.app.ticketservice.service.impl;

import com.app.ticketservice.ModelUtils;
import com.app.ticketservice.TicketServiceApplication;
import com.app.ticketservice.dto.BusRouteCreateUpdateRequest;
import com.app.ticketservice.dto.BusRouteResponse;
import com.app.ticketservice.service.BusRouteService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = TicketServiceApplication.class)
@TestPropertySource("/application-test.properties")
@Sql(scripts = "/insert.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/delete.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
class BusRouteServiceImplTest {

    @Autowired
    private BusRouteService busRouteService;

    @Test
    void findAll() {
       Assertions.assertEquals(2, busRouteService.findAll(PageRequest.of(0, 2)).size());
    }

    @Test
    void save() {
        BusRouteCreateUpdateRequest busRoute = ModelUtils.getBusRouteCreateUpdateRequest();
        BusRouteResponse busRouteResponse = busRouteService.save(busRoute);
        Assertions.assertNotNull(busRouteResponse);
        Assertions.assertTrue(busRouteService.findAll(PageRequest.of(0,5)).size()>1);
    }

    @Test
    void updateExistingBusRoute() {
        BusRouteResponse busRouteBeforeUpdate = busRouteService.findAll(PageRequest.of(0,1)).get(0);
        BusRouteCreateUpdateRequest busRouteUpdate = BusRouteCreateUpdateRequest
                .builder()
                .arrival(busRouteBeforeUpdate.getArrival()+1)
                .departure(busRouteBeforeUpdate.getDeparture()+1)
                .availableTickets(busRouteBeforeUpdate.getAvailableTickets()+1)
                .departureTime(busRouteBeforeUpdate.getDepartureTime())
                .ticketPrice(busRouteBeforeUpdate.getTicketPrice())
                .build();
        BusRouteResponse busRouteAfterUpdate = busRouteService.update(busRouteBeforeUpdate.getId(), busRouteUpdate);
        assertNotEquals(busRouteBeforeUpdate, busRouteAfterUpdate);
    }

    @Test
    void updateNonExistentBusRoute() {
        BusRouteCreateUpdateRequest busRouteUpdate = ModelUtils.getBusRouteCreateUpdateRequest();
        Assertions.assertThrows(IllegalArgumentException.class,()->busRouteService.update(-1L,busRouteUpdate));
    }

    @Test
    void cancelTicketOccupation() {
        List<Long> ticketsId = List.of(1001L,1002L);
        Assertions.assertTrue(busRouteService.cancelTicketOccupation(ticketsId)>0);
    }
}