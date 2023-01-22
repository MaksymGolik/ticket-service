package com.app.ticketservice.controller;

import com.app.ticketservice.dto.BusRouteCreateRequest;
import com.app.ticketservice.dto.BusRouteResponse;
import com.app.ticketservice.model.BusRoute;
import com.app.ticketservice.service.BusRouteService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<BusRouteResponse> addBusRoute(@Valid @RequestBody BusRouteCreateRequest busRouteCreateRequest){
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(busRouteService.save(busRouteCreateRequest));
    }
}
