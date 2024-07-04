package com.eventBooker.services.interfaces;

import com.eventBooker.dtos.request.*;
import com.eventBooker.dtos.response.*;

import java.util.List;

public interface OrganizerService {
    OrganizerResponse register(OrganizerRegisterRequest request);
    EventResponse createEvent(CreateEventRequest createRequest);
    AddTicketResponse addTicketToEvent(AddTicketRequest addTicketRequest);
    DiscountTicketResponse discountTicket(DiscountTicketRequest request);
    List<EventResponse> getAllEventAttendees(ViewEventsRequest request);
    EventResponse reserveTicket(ReserveTicket reserveTicket);
}
