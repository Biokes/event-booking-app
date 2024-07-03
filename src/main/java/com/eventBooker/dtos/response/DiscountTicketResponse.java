package com.eventBooker.dtos.response;

import com.eventBooker.data.models.TicketType;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
public class DiscountTicketResponse {
    private BigDecimal price;
    private TicketType ticketType;
}
