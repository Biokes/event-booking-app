package com.eventBooker.dtos.request;

import com.eventBooker.data.models.TicketType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
@Builder
@AllArgsConstructor
public class AddTicketRequest {
    private Long eventId;
    private TicketType ticketType;
    private BigDecimal price;
}
