package com.eventBooker.services.interfaces;


import com.eventBooker.dtos.request.AttendeeReserveRequest;
import com.eventBooker.dtos.request.BuyTicketRequest;
import com.eventBooker.dtos.response.BookTicketResponse;
import com.eventBooker.dtos.response.ReserveTicketResponse;

public interface AttendeeService {
    BookTicketResponse bookTicket(BuyTicketRequest request);

    ReserveTicketResponse reserveTicket(AttendeeReserveRequest request);
}
