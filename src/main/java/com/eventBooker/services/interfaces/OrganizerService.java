package com.eventBooker.services.interfaces;

import com.eventBooker.dtos.request.AddTicketRequest;
import com.eventBooker.dtos.request.CreateEventRequest;
import com.eventBooker.dtos.request.OrganizerRegisterRequest;
import com.eventBooker.dtos.response.AddTicketResponse;
import com.eventBooker.dtos.response.CreateEventResponse;
import com.eventBooker.dtos.response.OrganizerResponse;

public interface OrganizerService {
    OrganizerResponse register(OrganizerRegisterRequest request);
    CreateEventResponse createEvent(CreateEventRequest createRequest);

    AddTicketResponse addTicketToEvent(AddTicketRequest addTicketRequest);
}
