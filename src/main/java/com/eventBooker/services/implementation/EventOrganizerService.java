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

import static com.eventBooker.exception.Messages.*;

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
    public OrganizerResponse register(OrganizerRegisterRequest request){
        try {
            return registerOrganizer(request);
        }
        catch (DataIntegrityViolationException exception){
            throw new EventException(DETAILS_ALREADY_EXIST.getMessage());
        }
    }
    @Override
    public EventResponse createEvent(CreateEventRequest createRequest){
        try {
            Organizer  organizer = organizerRepository.findByEmail(createRequest.getEmail()).orElseThrow(()->new EventException(INVALID_DETAILS.getMessage()));
            Event event = eventService.createEvent(createRequest,organizer);
            EventResponse response = modelMapper.map(event, EventResponse.class);
            response.setEmail(organizer.getEmail());
            return response;
        }
        catch (DataIntegrityViolationException exception){
            throw new EventException(DETAILS_ALREADY_EXIST.getMessage());
        }
    }
    @Override
    public AddTicketResponse addTicketToEvent(AddTicketRequest addTicketRequest) {
        try {
            return eventService.createTicket(addTicketRequest);
        } catch (DataIntegrityViolationException exception) {
            throw new EventException(DETAILS_ALREADY_EXIST.getMessage());
        }
    }
    @Override
    public DiscountTicketResponse discountTicket(DiscountTicketRequest request){
        try {
            Organizer organizer = organizerRepository.findByEmail(request.getEmail())
                    .orElseThrow(()->new EventException(INVALID_DETAILS.getMessage()));
            comparePassword(organizer,request.getPassword());
            return eventService.discountTicket(request);
        }
        catch (DataIntegrityViolationException exception){
            throw new EventException(DETAILS_ALREADY_EXIST.getMessage());
        }
    }
    @Override
    public List<EventResponse> getAllEventAttendees(ViewEventsRequest request) {
        try {
            Organizer organizer =organizerRepository.findByEmail(request.getEmail())
                    .orElseThrow(()->new EventException(INVALID_DETAILS.getMessage()));
            return eventService.viewAllOrganizerEvents(organizer);
        }
        catch (DataIntegrityViolationException exception){
            throw new EventException(DETAILS_ALREADY_EXIST.getMessage());
        }
    }
    @Override
    public EventResponse reserveTicket(ReserveTicket reserveTicket) {
        try {
            Event event =  eventService.findEventById(reserveTicket.getEventId());
            return eventService.reserveTicketsForAttendees(reserveTicket,event);
        }
        catch (DataIntegrityViolationException exception){
            throw new EventException(DETAILS_ALREADY_EXIST.getMessage());
        }
    }
    @Override
    public AddGuestResponse addGuestToEvent(AddGuestRequest request){
        try {
            return eventService.addEventGuest(request);
        }
        catch (DataIntegrityViolationException exception){
            throw new EventException(SOMETHING_WENT_WRONG.getMessage());
        }
    }
    @Override
    public List<AttendeeResponse> getEventAttendees(Long eventId) {
        try {
            return eventService.getEventAttendees(eventId);
        }
        catch (DataIntegrityViolationException exception){
            throw new EventException(SOMETHING_WENT_WRONG.getMessage());
        }
    }
    private void comparePassword(Organizer organizer,String password) {
        if(!encoder.matches(password, organizer.getPassword()))
            throw new EventException(INVALID_DETAILS.getMessage());
    }
    private OrganizerResponse registerOrganizer(OrganizerRegisterRequest request) {
        try {
            request.setPassword(encoder.encode(request.getPassword()));
            Organizer organizer = modelMapper.map(request, Organizer.class);
            organizer = organizerRepository.save(organizer);
            return modelMapper.map(organizer, OrganizerResponse.class);
        }
        catch (DataIntegrityViolationException exception){
            throw new EventException(SOMETHING_WENT_WRONG.getMessage());
        }
    }
}
