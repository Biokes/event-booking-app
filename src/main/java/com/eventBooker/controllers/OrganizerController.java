package com.eventBooker.controllers;
import com.eventBooker.dtos.request.OrganizerRegisterRequest;
import com.eventBooker.services.interfaces.OrganizerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.CREATED;
@RestController
@RequestMapping("/api/v1")
public class OrganizerController {
    @Autowired
    private OrganizerService organizerService;
    @RequestMapping("/register/organizer")
    public ResponseEntity<?> registerAsOrganizer(@RequestBody OrganizerRegisterRequest registerRequest){
        return ResponseEntity.status(CREATED).body(organizerService.register(registerRequest));
    }
}
