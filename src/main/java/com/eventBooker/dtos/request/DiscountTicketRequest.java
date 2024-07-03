package com.eventBooker.dtos.request;

import com.eventBooker.data.models.EventType;
import com.eventBooker.data.models.TicketType;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DiscountTicketRequest {
    @NotNull
    private BigDecimal discountPrice;
    @NotNull
    private Long eventId;
    @NotNull
    private TicketType ticketType;
    @NotNull
    private String email;
    @NotNull
    private String password;
}
