package com.eventBooker.services.interfaces;

import com.eventBooker.data.models.Event;
import com.eventBooker.data.models.Organizer;
import com.eventBooker.dtos.request.*;
import com.eventBooker.dtos.response.*;

import java.util.List;

public interface EventService {
    Event createEvent(CreateEventRequest createRequest, Organizer organizer);
    AddTicketResponse createTicket(AddTicketRequest addTicketRequest);
    DiscountTicketResponse discountTicket(DiscountTicketRequest request);
    List<EventResponse> viewAllOrganizerEvents(Organizer organizer);
    Event findEventById(Long eventId);
    EventResponse reserveTicketsForAttendees(ReserveTicket reserveTicket, Event event);
    AddGuestResponse addEventGuest(AddGuestRequest request);
    List<AttendeeResponse> getEventAttendees(Long eventId);
}
