package com.eventBooker.services.implementation;

import com.eventBooker.data.models.Event;
import com.eventBooker.dtos.request.AddTicketRequest;
import com.eventBooker.dtos.request.DiscountTicketRequest;
import com.eventBooker.dtos.response.AddTicketResponse;
import com.eventBooker.dtos.response.DiscountTicketResponse;
import com.eventBooker.exception.EventException;
import com.eventBooker.services.interfaces.EventService;
import com.eventBooker.services.interfaces.OrganizerService;
import com.eventBooker.data.models.Organizer;
import com.eventBooker.data.repo.OrganizerRepository;
import com.eventBooker.dtos.request.CreateEventRequest;
import com.eventBooker.dtos.request.OrganizerRegisterRequest;
import com.eventBooker.dtos.response.CreateEventResponse;
import com.eventBooker.dtos.response.OrganizerResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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
        }catch (DataIntegrityViolationException exception){
            throw new EventException(DETAILS_ALREADY_EXIST.getMessage());
        }
    }
    @Override
    public CreateEventResponse createEvent(CreateEventRequest createRequest) {
        Organizer  organizer = organizerRepository.findByEmail(createRequest.getEmail()).orElseThrow(()->new EventException(INVALID_DETAILS.getMessage()));
        Event event = eventService.createEvent(createRequest,organizer);
        CreateEventResponse response = modelMapper.map(event, CreateEventResponse.class);
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
