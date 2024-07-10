package com.eventBooker.handlers;

import com.eventBooker.exception.EventException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

import static com.eventBooker.exception.Messages.INVALID_DETAILS;
import static com.eventBooker.exception.Messages.SOMETHING_WENT_WRONG;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@RestControllerAdvice
public class GlobalException {
    @ExceptionHandler(EventException.class)
    @ResponseBody
    public ResponseEntity<?> handleEventException(EventException exception){
        return ResponseEntity.status(BAD_REQUEST)
                .body(Map.of("Error",exception.getMessage(),"success",false));
    }
    @ExceptionHandler(BadCredentialsException.class)
    @ResponseBody
    public ResponseEntity<?> handleBadCredentialsException(BadCredentialsException error){
        return ResponseEntity.status(BAD_REQUEST)
                .body(Map.of("Error",error.getMessage(),"success",false));
    }
    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseBody
    public ResponseEntity<?> handleDataIntegrityViolationException(DataIntegrityViolationException error){
        return ResponseEntity.status(BAD_REQUEST)
                .body(Map.of("Error",error.getMessage(),"success",false));
    }
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseEntity<?> handleEventExceptions(){
        return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                .body(Map.of("error", SOMETHING_WENT_WRONG.getMessage(),"success", false));
    }
}
