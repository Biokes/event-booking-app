package com.eventBooker.services.interfaces;


import com.eventBooker.dtos.request.BuyTicketRequest;
import com.eventBooker.dtos.response.BookTicketResponse;

public interface AttendeeService {
    BookTicketResponse bookTicket(BuyTicketRequest request);
}
