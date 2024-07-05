package com.eventBooker.handlers;

import com.eventBooker.exception.EventException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@RestControllerAdvice
public class GlobalException {
    @ExceptionHandler(EventException.class)
    @ResponseBody
    public ResponseEntity<?> registerOrganizer(EventException exception){
        return ResponseEntity.status(BAD_REQUEST)
                .body(Map.of("Error",exception.getMessage(),"success",false));
    }
    @ExceptionHandler(BadCredentialsException.class)
    @ResponseBody
    public ResponseEntity<?> authenticate(BadCredentialsException error){
        return ResponseEntity.status(BAD_REQUEST)
                .body(Map.of("Error",error.getMessage(),"success",false));
    }

}
