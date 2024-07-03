package com.eventBooker.services.implementation;

import com.eventBooker.dtos.request.BuyTicketRequest;
import com.eventBooker.dtos.response.BookTicketResponse;
import com.eventBooker.services.interfaces.AttendeeService;
import org.springframework.stereotype.Service;

@Service
public class EventAttendeeService implements AttendeeService {
    @Override
    public BookTicketResponse bookTicket(BuyTicketRequest request){
        return null;
    }
}
