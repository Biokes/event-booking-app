package com.eventBooker.exception;

import lombok.Getter;

@Getter
public enum Messages {
    TICKET_ALREADY_EXIST("Ticket already exist"),
    INVALID_DETAILS("Invalid Details provided"),
    DETAILS_ALREADY_EXIST("Details Provided already exist or is invalid(Organiser Email)");

    final String message;
    Messages(String message){
        this.message = message;
    }

}
