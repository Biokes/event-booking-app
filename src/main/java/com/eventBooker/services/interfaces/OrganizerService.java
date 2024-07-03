package com.eventBooker.services.interfaces;

import com.eventBooker.dtos.request.*;
import com.eventBooker.dtos.response.*;

import java.util.List;

public interface OrganizerService {
    OrganizerResponse register(OrganizerRegisterRequest request);
    CreateEventResponse createEvent(CreateEventRequest createRequest);
    AddTicketResponse addTicketToEvent(AddTicketRequest addTicketRequest);
    DiscountTicketResponse discountTicket(DiscountTicketRequest request);
    List<AttendeeResponse> getAllEventAttendees(ViewEventsRequest request);
}
