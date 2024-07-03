package com.eventBooker.ServicesTest;

import com.eventBooker.data.models.TicketType;
import com.eventBooker.dtos.request.AddTicketRequest;
import com.eventBooker.dtos.response.AddTicketResponse;
import com.eventBooker.exception.EventException;
import com.eventBooker.services.interfaces.OrganizerService;
import com.eventBooker.dtos.request.CreateEventRequest;
import com.eventBooker.dtos.request.OrganizerRegisterRequest;
import com.eventBooker.dtos.response.CreateEventResponse;
import com.eventBooker.dtos.response.OrganizerResponse;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static com.eventBooker.data.models.EventType.WEDDING;
import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
public class OrganiserTest {
    @Autowired
    private OrganizerService organizerService;
    @Test
    void testOrganizersCanRegister(){
        OrganizerRegisterRequest request = OrganizerRegisterRequest.builder().
                email("abbey@gmail.com").phoneNumber("+234508978679").password("Password12.").build();
        assertThrows(EventException.class, ()->organizerService.register(request));
        request.setEmail("ab0@gmail.com");
        OrganizerResponse response = organizerService.register(request);
        assertNotNull(response.getId());
        assertNotNull(response.getPhoneNumber());
        assertNotNull(response.getEmail());
    }
    @Test
    void testOrganizerCanCreateEvent(){
        CreateEventRequest createRequest = CreateEventRequest.builder().eventType(WEDDING).
                startTime(LocalDateTime.parse("2023-12-03T10:15:30"))
                .endTime(LocalDateTime.parse("2023-12-03T20:00:00"))
                .email("abbey@gmail.com").build();
        CreateEventResponse response = organizerService.createEvent(createRequest);
        log.info("Response----> {}",response);
        assertNotNull(response.getId());
        assertNotNull(response.getEndDate());
        assertNotNull(response.getStartDate());
        assertNotNull(response.getEmail());
    }
    @Test
    void testTicketCanBeAddedToRequest(){
        AddTicketRequest addTicketRequest = AddTicketRequest.builder().eventId(7L)
                .ticketType(TicketType.VVIP).price(new BigDecimal("900000")).build();
        assertThrows(EventException.class,()->organizerService.addTicketToEvent(addTicketRequest));
        addTicketRequest.setEventId(52L);
        AddTicketResponse response = organizerService.addTicketToEvent(addTicketRequest);
        assertNotNull(response);
        assertNotNull(response.getTicketType());
    }
}
