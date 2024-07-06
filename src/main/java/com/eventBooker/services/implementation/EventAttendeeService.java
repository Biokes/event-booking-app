package com.eventBooker.services.implementation;

import com.eventBooker.data.models.Attendee;
import com.eventBooker.data.models.Event;
import com.eventBooker.data.models.Ticket;
import com.eventBooker.data.repo.EventRepository;
import com.eventBooker.data.repo.TicketRepository;
import com.eventBooker.dtos.request.AttendeeReserveRequest;
import com.eventBooker.dtos.request.BuyTicketRequest;
import com.eventBooker.dtos.response.BookTicketResponse;
import com.eventBooker.dtos.response.ReserveTicketResponse;
import com.eventBooker.exception.EventException;
import com.eventBooker.services.interfaces.AttendeeService;
import com.eventBooker.services.interfaces.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

import static com.eventBooker.exception.Messages.INVALID_DETAILS;
import static com.eventBooker.exception.Messages.NO_AVAILABLE_TICKET;

@Service
public class EventAttendeeService implements AttendeeService {
    @Autowired
    private TicketRepository ticketRepository;
    @Autowired
    private EventRepository eventRepository;
    @Override
    @Transactional
    public BookTicketResponse bookTicket(BuyTicketRequest request){
       Event event = eventRepository.findById(request.getEventId()).orElseThrow(()->new EventException(INVALID_DETAILS.getMessage()));
        Ticket ticket = ticketRepository.findByEventAndTicketType(event,request.getTicketType()).orElseThrow(()->new EventException(INVALID_DETAILS.getMessage()));
        validateAndSell(request, ticket);
        Attendee attendee = Attendee.builder().age(request.getAge()).name(request.getName()).ticket(ticket).build();
       return BookTicketResponse.builder().id(attendee.getId()).startTime(event.getStartDate()).endTime(event.getEndTime()).ticketType(ticket.getTicketType()).build();
    }

    @Override
    public ReserveTicketResponse reserveTicket(AttendeeReserveRequest request) {
        Event event = eventRepository.findById(request.getEventId()).orElseThrow(EventException::new);
        Ticket ticket = ticketRepository.findByEventAndTicketType(event,request.getTicketType()).orElseThrow(EventException::new);

        return null;
    }

    private void validateAndSell(BuyTicketRequest request, Ticket ticket) {
        validateTicketBooking(ticket);
        validatePrice(request.getPrice(), ticket.getPrice());
        ticket.setTotal(ticket.getTotal()-1);
        ticketRepository.save(ticket);
    }

    private void validateTicketBooking(Ticket ticket) {
        if(ticket.getTotal()==0)
            throw new EventException(NO_AVAILABLE_TICKET.getMessage());
    }

    private void validatePrice(BigDecimal price, BigDecimal price1) {
        if(price1.doubleValue()<price.doubleValue())
            throw new EventException(INVALID_DETAILS.getMessage());
    }
}
