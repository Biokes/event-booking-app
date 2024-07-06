package com.eventBooker.dtos.request;

import com.eventBooker.data.enums.TicketType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AttendeeReserveRequest{
    @NotNull
    @Min(0)
    private Long eventId;
    @NotNull
    private String attendeeName;
    @NotNull
    private TicketType ticketType;
}
