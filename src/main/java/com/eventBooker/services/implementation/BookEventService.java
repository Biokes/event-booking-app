package com.eventBooker.services.implementation;

import com.eventBooker.data.enums.Role;
import com.eventBooker.data.models.Attendee;
import com.eventBooker.data.models.Event;
import com.eventBooker.data.models.Organizer;
import com.eventBooker.data.models.Ticket;
import com.eventBooker.data.repo.AttendeeRepository;
import com.eventBooker.data.repo.EventRepository;
import com.eventBooker.data.repo.TicketRepository;
import com.eventBooker.dtos.request.*;
import com.eventBooker.dtos.response.*;
import com.eventBooker.exception.EventException;
import com.eventBooker.services.interfaces.EventService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.eventBooker.data.enums.Role.INVITED_GUEST;
import static com.eventBooker.data.enums.TicketStatus.BOOKED;
import static com.eventBooker.data.enums.TicketType.GUEST;
import static com.eventBooker.exception.Messages.*;

@Service
@Slf4j
public class BookEventService implements EventService {
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private EventRepository eventRepository;
    @Autowired
    private TicketRepository ticketRepo;
    @Autowired
    private AttendeeRepository attendeeRepository;
    @Override
    public Event createEvent(CreateEventRequest createRequest, Organizer organizer) {
        Event event = modelMapper.map(createRequest, Event.class);
        event.setOrganizer(organizer);
        return eventRepository.save(event);
    }
    @Override
    public AddTicketResponse createTicket(AddTicketRequest addTicketRequest){
        Event event = eventRepository.findById(addTicketRequest.getEventId()).orElseThrow(()->new EventException(INVALID_DETAILS.getMessage()));
        checkDuplicateTicket(addTicketRequest, event);
        Ticket ticket = modelMapper.map(addTicketRequest, Ticket.class);
        ticket.setId(null);
        ticket.setEvent(event);
        ticket = ticketRepo.save(ticket);
        return modelMapper.map(ticket, AddTicketResponse.class);
    }
    @Override
    public DiscountTicketResponse discountTicket(DiscountTicketRequest request) {
        Event event = eventRepository.findById(request.getEventId()).orElseThrow(()->new EventException(INVALID_DETAILS.getMessage()));
        Ticket ticket = ticketRepo.findByEventAndTicketType(event,request.getTicketType()).orElseThrow(()->new EventException(INVALID_DETAILS.getMessage()));
        validatePrice(ticket.getPrice(),request.getDiscountPrice());
        ticket.setPrice(ticket.getPrice().subtract(request.getDiscountPrice()));
        ticket = ticketRepo.save(ticket);
        return modelMapper.map(ticket, DiscountTicketResponse.class);
    }

    private void validatePrice(BigDecimal price, BigDecimal discountPrice) {
        if(discountPrice.doubleValue() >= price.doubleValue())
            throw  new EventException(INVALID_DETAILS.getMessage());
    }

    @Override
    public List<EventResponse> viewAllOrganizerEvents(Organizer organizer) {
        List<Event> events = eventRepository.findAllByOrganizer(organizer);
        if(events.isEmpty())throw new EventException(NO_TICKET_BOOKED.getMessage());
        List<EventResponse> response = new ArrayList<>();
        events.forEach(event ->{response.add(modelMapper.map(event,EventResponse.class));});
        return response;
    }
    @Override
    public Event findEventById(Long eventId){
        return eventRepository.findById(eventId).orElseThrow(()->new EventException(INVALID_DETAILS.getMessage()));
    }
    @Override
    @Transactional
    public EventResponse reserveTicketsForAttendees(ReserveTicket reserveTicket, Event event){
        Ticket ticket = ticketRepo.findByEventAndTicketType(event,reserveTicket.getTicketType()).orElseThrow(()->new EventException(INVALID_DETAILS.getMessage()));
        checkTicket(ticket);
        ticket.setTotal(ticket.getTotal()-reserveTicket.getQuantity());
        ticket=ticketRepo.save(ticket);
        return EventResponse.builder()
                .eventType(ticket.getEvent().getEventType()).total((long) ticket.getTotal())
                .endDate(event.getEndTime())
                .id(event.getId()).email(event.getOrganizer().getEmail())
                .startDate(event.getStartDate()).build();
    }

    @Override
    public AddGuestResponse addEventGuest(AddGuestRequest request) {
        Event event = findEventById(request.getEventId());
        Ticket ticket = getGuestTicket(request,event);
        Attendee guest = Attendee.builder().status(BOOKED).name(request.getGuestName())
                .ticket(ticket).role(INVITED_GUEST).build();
        guest = attendeeRepository.save(guest);
        AddGuestResponse response = modelMapper.map(guest, AddGuestResponse.class);
        response.setEndTime(event.getEndTime());response.setStartDate(event.getStartDate());
        return response;
    }

    @Override
    public List<AttendeeResponse> getEventAttendees(Long eventId) {
        findEventById(eventId);
        return attendeeRepository.findAll().stream()
                .filter(attendee -> attendee.getTicket().getEvent().getId().equals(eventId))
                .map(attendee -> modelMapper.map(attendee,AttendeeResponse.class)).toList();
    }

    private Ticket getGuestTicket(AddGuestRequest request,Event event) {
        Optional<Ticket> ticket = ticketRepo.findByEventAndTicketType(event,request.getTicketType());
        if(ticket.isEmpty()) ticket = Optional.of(createGuestTicket(event));
        return ticket.get();
    }

    private Ticket createGuestTicket(Event event) {
        Ticket ticket = new Ticket();
        ticket.setTicketType(GUEST);
        ticket.setEvent(event);
        ticket.setPrice(new BigDecimal("0"));
        ticket.setTotal(50);
        return ticketRepo.save(ticket);
    }

    private void checkTicket(Ticket ticket) {
        if(ticket.getTotal()==0)
            throw new EventException(NO_AVAILABLE_TICKET.getMessage());
    }

    private void checkDuplicateTicket(AddTicketRequest addTicketRequest, Event event) {
       if(ticketRepo.findByEventAndTicketType(event,addTicketRequest.getTicketType()).isPresent())
           throw new EventException(TICKET_ALREADY_EXIST.getMessage());
    }
}
