package com.eventBooker.dtos.response;

import com.eventBooker.data.models.EventType;
import lombok.*;

import java.time.LocalDateTime;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class EventResponse {
    private Long id;
    private EventType eventType;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String email;
    private Long total;
    private int reserved;
}
