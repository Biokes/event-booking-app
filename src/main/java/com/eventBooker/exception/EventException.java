package com.eventBooker.exception;

import static com.eventBooker.exception.Messages.INVALID_DETAILS;

public class EventException extends RuntimeException{
    public EventException(String errorMessage){
        super(errorMessage);
    }
    public EventException(){
        super(INVALID_DETAILS.getMessage());
    }
}
