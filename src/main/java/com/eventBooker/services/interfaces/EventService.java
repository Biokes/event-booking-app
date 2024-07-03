package com.eventBooker.services.interfaces;

import com.eventBooker.data.models.Event;
import com.eventBooker.data.models.Organizer;
import com.eventBooker.dtos.request.AddTicketRequest;
import com.eventBooker.dtos.request.CreateEventRequest;
import com.eventBooker.dtos.request.DiscountTicketRequest;
import com.eventBooker.dtos.response.AddTicketResponse;
import com.eventBooker.dtos.response.AttendeeResponse;
import com.eventBooker.dtos.response.DiscountTicketResponse;

import java.util.List;

public interface EventService {
    Event createEvent(CreateEventRequest createRequest, Organizer organizer);

    AddTicketResponse createTicket(AddTicketRequest addTicketRequest);

    DiscountTicketResponse discountTicket(DiscountTicketRequest request);

    List<AttendeeResponse> findAllByOrganizer(Organizer organizer);
}
