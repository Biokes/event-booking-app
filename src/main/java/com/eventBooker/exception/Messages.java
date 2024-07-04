package com.eventBooker.exception;

import lombok.Getter;

@Getter
public enum Messages {
    NO_AVAILABLE_TICKET("ticket is currently not available"),
    NO_TICKET_BOOKED("No ticket booked Yet"),
    TICKET_ALREADY_EXIST("Ticket already exist"),
    INVALID_DETAILS("Invalid Details provided"),
    DETAILS_ALREADY_EXIST("Details Provided already exist or is invalid(Organiser Email)");

    final String message;
    Messages(String message){
        this.message = message;
    }

}
