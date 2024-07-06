package com.eventBooker.dtos.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Setter
@Getter
@ToString
@Builder
public class ViewEventsRequest {
    private String  email;

}
