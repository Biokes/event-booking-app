package com.eventBooker.services.implementation;

import com.eventBooker.data.models.Event;
import com.eventBooker.dtos.request.*;
import com.eventBooker.dtos.response.*;
import com.eventBooker.exception.EventException;
import com.eventBooker.services.interfaces.EventService;
import com.eventBooker.services.interfaces.OrganizerService;
import com.eventBooker.data.models.Organizer;
import com.eventBooker.data.repo.OrganizerRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.eventBooker.exception.Messages.DETAILS_ALREADY_EXIST;
import static com.eventBooker.exception.Messages.INVALID_DETAILS;

@Service
public class EventOrganizerService implements OrganizerService {
    @Autowired
    private OrganizerRepository organizerRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private PasswordEncoder encoder;
    @Autowired
    private EventService eventService;
    @Override
    public OrganizerResponse register(OrganizerRegisterRequest request) {
        try {
            return registerOrganizer(request);
        }
        catch (DataIntegrityViolationException exception){
            throw new EventException(DETAILS_ALREADY_EXIST.getMessage());
        }
    }
    @Override
    public EventResponse createEvent(CreateEventRequest createRequest) {
        Organizer  organizer = organizerRepository.findByEmail(createRequest.getEmail()).orElseThrow(()->new EventException(INVALID_DETAILS.getMessage()));
        Event event = eventService.createEvent(createRequest,organizer);
        EventResponse response = modelMapper.map(event, EventResponse.class);
        response.setEmail(organizer.getEmail());
        return response;
    }

    @Override
    public AddTicketResponse addTicketToEvent(AddTicketRequest addTicketRequest) {
         return eventService.createTicket(addTicketRequest);
    }

    @Override
    public DiscountTicketResponse discountTicket(DiscountTicketRequest request) {
        Organizer organizer = organizerRepository.findByEmail(request.getEmail())
                .orElseThrow(()->new EventException(INVALID_DETAILS.getMessage()));
        comparePassword(organizer,request.getPassword());
        return eventService.discountTicket(request);
    }

    @Override
    public List<EventResponse> getAllEventAttendees(ViewEventsRequest request) {
        Organizer organizer =organizerRepository.findByEmail(request.getEmail())
                .orElseThrow(()->new EventException(INVALID_DETAILS.getMessage()));
        return eventService.viewAllOrganizerEvents(organizer);
    }

    @Override
    public EventResponse reserveTicket(ReserveTicket reserveTicket) {
       Event event =  eventService.findEventById(reserveTicket.getEventId());
        return eventService.reserveTicket(reserveTicket,event);
    }

    private void comparePassword(Organizer organizer,String password) {
        if(!encoder.matches(password, organizer.getPassword()))
            throw new EventException(INVALID_DETAILS.getMessage());
    }

    private OrganizerResponse registerOrganizer(OrganizerRegisterRequest request) {
        request.setPassword(encoder.encode(request.getPassword()));
        Organizer organizer = modelMapper.map(request, Organizer.class);
        organizer = organizerRepository.save(organizer);
        return modelMapper.map(organizer, OrganizerResponse.class);
    }
}
