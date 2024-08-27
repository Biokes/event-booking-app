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
import com.eventBooker.dtos.response.EventResponse;
import com.eventBooker.dtos.response.ReserveTicketResponse;
import com.eventBooker.exception.EventException;
import com.eventBooker.services.interfaces.AttendeeService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

import static com.eventBooker.data.enums.Role.ATTENDEE;
import static com.eventBooker.data.enums.TicketStatus.BOOKED;
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
       Attendee attendee = Attendee.builder().ticket(ticket).role(ATTENDEE).status(BOOKED).name(request.getName()).build();
       repository.save(attendee);
       return BookTicketResponse.builder().ticketType(ticket.getTicketType())
               .endTime(event.getEndTime())
               .startTime(event.getStartDate()).id(attendee.getId()).build();
    }
    @Override
    @Transactional
    public ReserveTicketResponse reserveTicket(AttendeeReserveRequest request) {
        Event event = eventRepository.findById(request.getEventId()).orElseThrow(EventException::new);
        Ticket ticket = ticketRepository.findByEventAndTicketType(event,request.getTicketType()).orElseThrow(EventException::new);
        Attendee attendee = mapAttendee(request, ticket);
        attendee=repository.save(attendee);
        return map(attendee,event);
    }

    @Override
    public List<EventResponse> getAllEvents(){
        return null;
    }

    private ReserveTicketResponse map(Attendee attendee, Event event) {
        ReserveTicketResponse response = modelMapper.map(attendee, ReserveTicketResponse.class);
        response.setStartDate(event.getStartDate());
        response.setEndTime(event.getEndTime());
        response.setEventId(event.getId());
        response.setEventType(event.getEventType());
        return response;
    }
    private static Attendee mapAttendee(AttendeeReserveRequest request, Ticket ticket) {
        return  Attendee.builder()
                .name(request.getAttendeeName())
                .role(ATTENDEE).ticket(ticket)
                .status(RESERVED).build();
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
