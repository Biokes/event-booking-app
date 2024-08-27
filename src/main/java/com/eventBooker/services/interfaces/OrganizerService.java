package com.eventBooker.services.interfaces;

import com.eventBooker.dtos.request.*;
import com.eventBooker.dtos.response.*;
import jakarta.validation.Valid;

import java.util.List;

public interface OrganizerService {
    OrganizerResponse register(@Valid OrganizerRegisterRequest request);
    EventResponse createEvent(@Valid CreateEventRequest createRequest);
    AddTicketResponse addTicketToEvent(@Valid AddTicketRequest addTicketRequest);
    DiscountTicketResponse discountTicket(@Valid DiscountTicketRequest request);
    List<EventResponse> getAllEventAttendees(@Valid ViewEventsRequest request);
    EventResponse reserveTicket(@Valid ReserveTicket reserveTicket);
    AddGuestResponse addGuestToEvent(@Valid AddGuestRequest request);
    List<AttendeeResponse> getEventAttendees(@Valid Long eventId);
    //TODO:
    // find all events
}
