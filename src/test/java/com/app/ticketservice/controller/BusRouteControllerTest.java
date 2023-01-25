package com.app.ticketservice.controller;

import com.app.ticketservice.ModelUtils;
import com.app.ticketservice.dto.BusRouteCreateUpdateRequest;
import com.app.ticketservice.dto.BusRouteResponse;
import com.app.ticketservice.mapper.BusRouteMapper;
import com.app.ticketservice.service.BusRouteService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class BusRouteControllerTest {
    @Mock
    private BusRouteService busRouteService;
    @InjectMocks
    private BusRouteController busRouteController;

    private static final String busRoutesLink = "/routes";
    private MockMvc mockMvc;
    private static final ObjectMapper objectMapper;
    static {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
    }

    @BeforeEach
    void setup() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(busRouteController)
                .build();
    }

    @Test
    void getAll() throws Exception {
        BusRouteResponse busRouteResponse = ModelUtils.getBusRouteResponse();
        String expected = objectMapper.writeValueAsString(List.of(busRouteResponse));
        when(busRouteService.findAll(any())).thenReturn(List.of(busRouteResponse));
        String actual = mockMvc.perform(get(busRoutesLink+"/all")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        verify(busRouteService, times(1)).findAll(any());
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void addBusRoute() throws Exception {
        BusRouteCreateUpdateRequest busRouteCreateUpdateRequest = ModelUtils.getBusRouteCreateUpdateRequest();
        when(busRouteService.save(any())).thenReturn(ModelUtils.getBusRouteResponse());
        String actual = mockMvc.perform(post(busRoutesLink+"/add")
                        .content(objectMapper.writeValueAsString(busRouteCreateUpdateRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();
        verify(busRouteService, times(1)).save(any());
        Assertions.assertEquals(objectMapper.writeValueAsString(ModelUtils.getBusRouteResponse()),actual);
    }
}