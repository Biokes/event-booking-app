package com.eventBooker.dtos.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BookTicketResponse {
    @JsonProperty(value = "id")
    private Long seat;
}
