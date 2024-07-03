package com.eventBooker.dtos.response;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class OrganizerResponse {
    private String email;
    private Long id;
    private String phoneNumber;
}
