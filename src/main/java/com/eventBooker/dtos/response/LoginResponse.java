package com.eventBooker.dtos.response;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class LoginResponse {
    private String message;
    private String token;
}
