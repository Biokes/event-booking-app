package com.eventBooker.dtos.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrganizerRegisterRequest {
    @Pattern(regexp = "^(?=[a-zA-Z])[a-zA-Z]+([0-9]*)([_+!`]*)+@(?=[a-zA-Z])([a-zA-Z]+)([0-9]*)([a-zA-Z0-9._!~+-]*)+\\.[a-zA-Z]{2,}$")
    private String email;
    @NotNull
    @Pattern(regexp = "^?\\+[0-9]{11,14}$",message = "Invalid Number")
    private String phoneNumber;
    @Pattern(regexp = "[a-zA-Z0-9]{8,}+[+-.,/]",message = "Alphanumerics and symbols is required")
    private String password;
}
