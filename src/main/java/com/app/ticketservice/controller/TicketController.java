package com.app.ticketservice.controller;

import com.app.ticketservice.dto.TicketCreateRequest;
import com.app.ticketservice.dto.TicketResponse;
import com.app.ticketservice.service.TicketService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/tickets")
@AllArgsConstructor
public class TicketController {

    private TicketService ticketService;

    @GetMapping("/{id}")
    public ResponseEntity<TicketResponse> getTicket(@PathVariable("id") Long id){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ticketService.findTicketById(id));
    }

    @PostMapping("/add")
    public ResponseEntity<?> addTicket(@Valid @RequestBody TicketCreateRequest ticketCreateRequest){
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ticketService.save(ticketCreateRequest).getId());
    }

}
