package com.eventBooker.services.implementation;

import com.eventBooker.data.models.Attendee;
import com.eventBooker.data.models.Event;
import com.eventBooker.data.models.Organizer;
import com.eventBooker.data.models.Ticket;
import com.eventBooker.data.repo.AttendeeRepository;
import com.eventBooker.data.repo.EventRepository;
import com.eventBooker.data.repo.OrganizerRepository;
import com.eventBooker.data.repo.TicketRepository;
import com.eventBooker.dtos.request.AttendeeReserveRequest;
import com.eventBooker.dtos.request.BuyTicketRequest;
import com.eventBooker.dtos.response.BookTicketResponse;
import com.eventBooker.dtos.response.ReserveTicketResponse;
import com.eventBooker.exception.EventException;
import com.eventBooker.services.interfaces.AttendeeService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

import static com.eventBooker.data.enums.Role.ATTENDEE;
import static com.eventBooker.data.enums.TicketStatus.RESERVED;
import static com.eventBooker.exception.Messages.INVALID_DETAILS;
import static com.eventBooker.exception.Messages.NO_AVAILABLE_TICKET;

@Service
@AllArgsConstructor
public class EventAttendeeService implements AttendeeService {
    private TicketRepository ticketRepository;
    private EventRepository eventRepository;
    private AttendeeRepository repository;
    private ModelMapper modelMapper;
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
        Attendee attendee = Attendee.builder().age(request.getAge()).name(request.getAttendeeName())
                .role(ATTENDEE).ticket(ticket).status(RESERVED).build();
        attendee=repository.save(attendee);
        return modelMapper.map(attendee,);
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
