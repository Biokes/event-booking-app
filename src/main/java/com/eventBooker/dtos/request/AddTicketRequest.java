package com.eventBooker.dtos.request;

import com.eventBooker.data.enums.TicketType;
import jakarta.validation.constraints.Min;
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
    @Min(0)
    private int total;
    @NotNull
    private TicketType ticketType;
    @NotNull
    private BigDecimal price;
}
