package com.eventBooker.dtos.request;

import com.eventBooker.data.enums.EventType;
import com.eventBooker.validator.AfterDateValidator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.time.LocalDateTime;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateEventRequest {
    private EventType eventType;
    @Future
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using= LocalDateDeserializer.class)
    private LocalDateTime startTime;
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using= LocalDateDeserializer.class)
    @AfterDateValidator(eventStartStart = "startTime")
    private LocalDateTime endTime;
    @JsonProperty("email")
    @Email(regexp="^(?=[a-zA-Z])[a-zA-Z]+([0-9]*)([_+!`]*)+@(?=[a-zA-Z])([a-zA-Z]+)([0-9]*)([a-zA-Z0-9._!~+-]*)+\\.[a-zA-Z]{2,}$")
    private String email;
}
