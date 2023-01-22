package com.app.ticketservice.service.impl;

import com.app.ticketservice.dto.BusRouteResponse;
import com.app.ticketservice.mapper.BusRouteMapper;
import com.app.ticketservice.dto.BusRouteCreateRequest;
import com.app.ticketservice.repository.BusRouteRepository;
import com.app.ticketservice.service.BusRouteService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class BusRouteServiceImpl implements BusRouteService {

    private BusRouteRepository busRouteRepository;

    @Override
    public List<BusRouteResponse> findAll(Pageable page) {
        return busRouteRepository.findAll(page)
                .stream()
                .map(BusRouteMapper::modelToResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public BusRouteResponse save(BusRouteCreateRequest busRouteCreateRequest) {
        return BusRouteMapper.modelToResponseDto(
                busRouteRepository.save(
                        BusRouteMapper.createDtoToModel(busRouteCreateRequest)));
    }
}
