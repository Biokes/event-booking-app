package com.eventBooker.controllers;

import com.eventBooker.dtos.request.AttendeeReserveRequest;
import com.eventBooker.dtos.request.BuyTicketRequest;
import com.eventBooker.dtos.request.ReserveTicket;
import com.eventBooker.services.interfaces.AttendeeService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.OK;

@RestController
@AllArgsConstructor
@CrossOrigin("*")
@RequestMapping("api/v1/attendee/")
public class AttendeeController {
    private AttendeeService attendeeService;
    @RequestMapping("bookTicket")
    public ResponseEntity<?> bookTicket(@RequestBody BuyTicketRequest request){
        return ResponseEntity.status(OK).body(attendeeService.bookTicket(request));
    }
    @RequestMapping("resreveTicket")
    public ResponseEntity<?> reserveTicket(@RequestBody AttendeeReserveRequest reserveTicket){
        return ResponseEntity.status(OK).body(attendeeService.reserveTicket(reserveTicket));
    }

}
