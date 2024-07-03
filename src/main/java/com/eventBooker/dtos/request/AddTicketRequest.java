package com.eventBooker.dtos.request;

import com.eventBooker.data.models.TicketType;
import jakarta.validation.constraints.NotNull;
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
    @NotNull
    private Long eventId;
    @NotNull
    private TicketType ticketType;
    @NotNull
    private BigDecimal price;
}
