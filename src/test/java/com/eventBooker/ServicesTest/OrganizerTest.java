package com.eventBooker.ServicesTest;

import com.eventBooker.dtos.request.*;
import com.eventBooker.dtos.response.*;
import com.eventBooker.exception.EventException;
import com.eventBooker.services.interfaces.AttendeeService;
import com.eventBooker.services.interfaces.OrganizerService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static com.eventBooker.data.models.EventType.WEDDING;
import static com.eventBooker.data.models.TicketType.*;
import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
public class OrganizerTest {
    @Autowired
    private OrganizerService organizerService;
    @Autowired
    private AttendeeService attendeeService;
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
        EventResponse response = organizerService.createEvent(createRequest);
        assertNotNull(response.getId());
        assertNotNull(response.getEndDate());
        assertNotNull(response.getStartDate());
        assertNotNull(response.getEmail());
    }
    @Test
    void testTicketCanBeAddedToEvent(){
        AddTicketRequest addTicketRequest = AddTicketRequest.builder().eventId(7L)
                .ticketType(REGULAR).total(1000).price(new BigDecimal("1000")).build();
        assertThrows(EventException.class,()->organizerService.addTicketToEvent(addTicketRequest));
        addTicketRequest.setEventId(1L);
        AddTicketResponse response = organizerService.addTicketToEvent(addTicketRequest);
        assertNotNull(response);
        assertNotNull(response.getTicketType());
    }
    @Test
    void testDiscountCanBeAddedToTicket(){
        DiscountTicketRequest request = DiscountTicketRequest.builder().email("abbey@gmail.com").ticketType(VVIP)
                .discountPrice(new BigDecimal("9900")).eventId(1L).password("Password12.").build();
        DiscountTicketResponse response = organizerService.discountTicket(request);
        assertNotNull(response);
        assertEquals(new BigDecimal("90000.00"),response.getPrice());
        assertEquals(request.getTicketType(),response.getTicketType());
    }
    @Test
    void testAttendeesCanPurchaseTicket(){
        BuyTicketRequest request = BuyTicketRequest.builder().price(new BigDecimal("90000.00")).eventId(1L)
                .ticketType(IRREGULAR).name("haio").age(80).build();
        assertThrows(EventException.class,()->attendeeService.bookTicket(request));
        request.setTicketType(VVIP);
        BookTicketResponse response = attendeeService.bookTicket(request);
        assertNotNull(response);
        assertNotNull(response.getEndTime());
        assertNotNull(response.getStartTime());
    }
    @Test
    void testOrganizerCanViewAllEvents(){
        ViewEventsRequest request = ViewEventsRequest.builder().email("abbey@gmail.com").build();
        List<EventResponse> response = organizerService.getAllEventAttendees(request);
        assertNotNull(response);
    }
    @Test
    void testOrganizerCanReserveTicket(){
        ReserveTicket reserveTicket = ReserveTicket.builder().ticketType(VVIP).quantity(1).eventId(1L).build();
        EventResponse response = organizerService.reserveTicket(reserveTicket);
        assertNotNull(response);
        assertNotNull(response.getEmail());
        assertEquals(48,response.getReserved());
        assertEquals(11,response.getTotal());
        assertNotNull(response.getEventType());
        assertNotNull(response.getId());
        assertNotNull(response.getEndDate());
        assertNotNull(response.getStartDate());
    }
//    @Test
//    void testOrganizerCanCreateGuestList(){
//        GuestListRequest request = GuestListRequest.builder().build();
//    }
}
