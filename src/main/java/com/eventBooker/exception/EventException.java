package com.eventBooker.exception;

public class EventException extends RuntimeException{
    public EventException(String errorMessage){
        super(errorMessage);
    }
}
