package com.eventBooker.dtos.request;

import lombok.*;

@Builder
@AllArgsConstructor
@Setter
@Getter
@NoArgsConstructor
public class LoginRequest {
    private String username;
    private  String password;
}
