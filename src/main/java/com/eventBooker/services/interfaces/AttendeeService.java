package com.eventBooker.services.interfaces;


import com.eventBooker.dtos.request.AttendeeReserveRequest;
import com.eventBooker.dtos.request.BuyTicketRequest;
import com.eventBooker.dtos.response.BookTicketResponse;
import com.eventBooker.dtos.response.EventResponse;
import com.eventBooker.dtos.response.ReserveTicketResponse;

import java.util.List;

public interface AttendeeService {
    BookTicketResponse bookTicket(BuyTicketRequest request);

    ReserveTicketResponse reserveTicket(AttendeeReserveRequest request);

    List<EventResponse> getAllEvents();
}
