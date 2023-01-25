package com.app.ticketservice.controller;

import com.app.ticketservice.dto.BusRouteCreateUpdateRequest;
import com.app.ticketservice.dto.BusRouteResponse;
import com.app.ticketservice.service.BusRouteService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/routes")
@AllArgsConstructor
public class BusRouteController {

    private BusRouteService busRouteService;

    @GetMapping("/all")
    public ResponseEntity<List<BusRouteResponse>> getAll(@RequestParam(defaultValue = "0") int page,
                                                         @RequestParam(defaultValue = "5") int size){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(busRouteService.findAll(PageRequest.of(page, size)));
    }

    @PostMapping("/add")
    public ResponseEntity<BusRouteResponse> addBusRoute(@Valid @RequestBody BusRouteCreateUpdateRequest busRouteCreateUpdateRequest){
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(busRouteService.save(busRouteCreateUpdateRequest));
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<BusRouteResponse> updateBusRoute(@PathVariable("id") Long id,
                                                           @Valid @RequestBody BusRouteCreateUpdateRequest busRouteCreateUpdateRequest){
        return ResponseEntity.status(HttpStatus.OK)
                .body(busRouteService.update(id, busRouteCreateUpdateRequest));
    }

    @PatchMapping("/cancel_ticket_occupation")
    public ResponseEntity<?> cancelTicketOccupation(@Valid @RequestBody List<Long> failedTickets){
        return ResponseEntity.ok(busRouteService.cancelTicketOccupation(failedTickets));
    }
}
