package com.eventBooker.services.implementation;

import com.eventBooker.data.models.Event;
import com.eventBooker.data.models.Organizer;
import com.eventBooker.data.models.Ticket;
import com.eventBooker.data.models.TicketType;
import com.eventBooker.data.repo.EventRepository;
import com.eventBooker.dtos.request.AddTicketRequest;
import com.eventBooker.dtos.request.CreateEventRequest;
import com.eventBooker.dtos.response.AddTicketResponse;
import com.eventBooker.exception.EventException;
import com.eventBooker.services.interfaces.EventService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.eventBooker.exception.Messages.INVALID_DETAILS;

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
        Ticket ticket = modelMapper.map(addTicketRequest, Ticket.class);
        ticket.setEvent(event);
        ticket = ticketRepo.save(ticket);
        return modelMapper.map(ticket, AddTicketResponse.class);
    }
}
