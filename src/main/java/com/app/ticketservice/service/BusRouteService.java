package com.app.ticketservice.service;

import com.app.ticketservice.dto.BusRouteResponse;
import com.app.ticketservice.dto.BusRouteCreateUpdateRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BusRouteService {
    List<BusRouteResponse> findAll(Pageable page);
    BusRouteResponse save(BusRouteCreateUpdateRequest busRouteCreateUpdateRequest);
    BusRouteResponse update(Long id, BusRouteCreateUpdateRequest busRouteCreateUpdateRequest);
}
