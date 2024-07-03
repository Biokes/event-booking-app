package com.eventBooker.services.implementation;

import com.eventBooker.data.models.Event;
import com.eventBooker.data.models.Organizer;
import com.eventBooker.data.models.Ticket;
import com.eventBooker.data.repo.EventRepository;
import com.eventBooker.data.repo.TicketRepository;
import com.eventBooker.dtos.request.AddTicketRequest;
import com.eventBooker.dtos.request.CreateEventRequest;
import com.eventBooker.dtos.request.DiscountTicketRequest;
import com.eventBooker.dtos.response.AddTicketResponse;
import com.eventBooker.dtos.response.DiscountTicketResponse;
import com.eventBooker.exception.EventException;
import com.eventBooker.services.interfaces.EventService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.eventBooker.exception.Messages.INVALID_DETAILS;
import static com.eventBooker.exception.Messages.TICKET_ALREADY_EXIST;

@Service
public class BookEventService implements EventService {
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private EventRepository eventRepository;
    @Autowired
    private TicketRepository ticketRepo;
    @Override
    public Event createEvent(CreateEventRequest createRequest, Organizer organizer) {
        Event event = modelMapper.map(createRequest, Event.class);
        event.setOrganizer(organizer);
        return eventRepository.save(event);
    }

    @Override
    public AddTicketResponse createTicket(AddTicketRequest addTicketRequest) {
        Event event = eventRepository.findById(addTicketRequest.getEventId())
                .orElseThrow(()->new EventException(INVALID_DETAILS.getMessage()));
        checkDuplicateTicket(addTicketRequest, event);
        Ticket ticket = modelMapper.map(addTicketRequest, Ticket.class);
        ticket.setEvent(event);
        ticket = ticketRepo.save(ticket);
        return modelMapper.map(ticket, AddTicketResponse.class);
    }

    @Override
    public DiscountTicketResponse discountTicket(DiscountTicketRequest request) {
        Event event = eventRepository.findById(request.getEventId()).orElseThrow(()->new EventException(INVALID_DETAILS.getMessage()));
        Ticket ticket = ticketRepo.findByEventAndTicketType(event,request.getTicketType()).orElseThrow(()->new EventException(INVALID_DETAILS.getMessage()));
        ticket.setPrice(ticket.getPrice().subtract(request.getDiscountPrice()));
        ticket = ticketRepo.save(ticket);
        return modelMapper.map(ticket, DiscountTicketResponse.class);
    }

    private void checkDuplicateTicket(AddTicketRequest addTicketRequest, Event event) {
       if(ticketRepo.findByEventAndTicketType(event,addTicketRequest.getTicketType()).isPresent())
           throw new EventException(TICKET_ALREADY_EXIST.getMessage());
    }
}
