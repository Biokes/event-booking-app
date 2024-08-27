package com.eventBooker.controllers;
import com.eventBooker.dtos.request.*;
import com.eventBooker.services.interfaces.OrganizerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/v1/organizer")
public class OrganizerController {
    @Autowired
    private OrganizerService organizerService;
    @RequestMapping("/register")
    public ResponseEntity<?> registerAsOrganizer(@RequestBody OrganizerRegisterRequest registerRequest){
        return ResponseEntity.status(CREATED).body(organizerService.register(registerRequest));
    }
    @RequestMapping("/create-event")
    public ResponseEntity<?> createEvent(@RequestBody CreateEventRequest request){
        return ResponseEntity.status(OK).body(organizerService.createEvent(request));
    }
    @RequestMapping("/addTicket")
    public ResponseEntity<?> createTicket(@RequestBody AddTicketRequest addTicketRequest){
        return ResponseEntity.status(OK)
                .body(organizerService.addTicketToEvent(addTicketRequest));
    }
    @RequestMapping("/discount-ticket")
    public ResponseEntity<?> discountTicket(@RequestBody DiscountTicketRequest request){
        return ResponseEntity.status(OK)
                .body(organizerService.discountTicket(request));
    }
    @RequestMapping("/getAllPartyAttendee")
    public ResponseEntity<?> getAllEventAttendees(@RequestBody ViewEventsRequest request){
        return ResponseEntity.status(OK)
                .body(organizerService.getAllEventAttendees(request));
    }
    @RequestMapping("/reserve-ticket")
    public ResponseEntity<?> reserveTicket(@RequestBody ReserveTicket request){
        return ResponseEntity.status(OK)
                .body(organizerService.reserveTicket(request));
    }
    // TODO:
    //    AddGuestResponse addGuestToEvent(AddGuestRequest request);
}
