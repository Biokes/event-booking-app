package com.eventBooker.dtos.response;

import com.eventBooker.data.models.EventType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Setter
@Getter
@ToString
public class CreateEventResponse {
    private Long id;
    private EventType eventType;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String email;
}
