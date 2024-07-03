package com.eventBooker.dtos.request;

import com.eventBooker.data.models.TicketType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.math.BigDecimal;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BuyTicketRequest {
    @NotNull
    private String name;
    @NotNull
    private BigDecimal price;
    @NotNull
    private Long eventId;
    @Positive
    @Min(value=18)
    private int age;
    @NotNull
    private TicketType ticketType;
}
