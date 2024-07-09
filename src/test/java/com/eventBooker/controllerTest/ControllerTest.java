package com.eventBooker.controllerTest;

import com.eventBooker.dtos.request.OrganizerRegisterRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Test
    void testOrganizersCanRegister() throws Exception {
        String request =
                "{\"email\":\"bioke3@gmail.com\",\"phoneNumber\":\"123456789098\", \"password\":\"Passkey12.\"}";
        mockMvc.perform(post("/api/v1/organizer/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(request))
                .andExpect(status().isCreated());
    }
    @Test
    void testAttendeeCanRegister() throws Exception{
        String request = "{\"name\":\"Sey\",\"eventId\":\"1\",\"price\":\"900\",\"ticketType\":\"VVIP\"}";
        mockMvc.perform(post("/api/v1/attendee/bookTicket")
                .contentType(MediaType.APPLICATION_JSON)
                .content(request))
                .andExpect(status().isOk());
    }
}
