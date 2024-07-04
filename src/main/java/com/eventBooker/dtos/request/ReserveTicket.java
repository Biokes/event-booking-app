package com.eventBooker.dtos.request;

import com.eventBooker.data.models.TicketType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Builder
@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ReserveTicket {
    @NotNull
    private TicketType ticketType;
    @Min(0)
    private int quantity;
    @Min(1)
    private Long eventId;
}
