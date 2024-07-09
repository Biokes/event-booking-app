package com.eventBooker.dtos.request;

import com.eventBooker.data.enums.TicketType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AddGuestRequest {
    @NotBlank
    private String guestName;
    @Min(1)
    private Long eventId;
    @NotNull
    private TicketType ticketType;
}

