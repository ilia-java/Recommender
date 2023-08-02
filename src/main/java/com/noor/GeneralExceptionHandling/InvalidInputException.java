package com.noor.GeneralExceptionHandling;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;

public class InvalidInputException extends RuntimeException {
    private static final Logger logger = LoggerFactory.getLogger(InvalidInputException.class);

    public InvalidInputException(String message, InvalidInputException ex) {

    }

    public InvalidInputException(String message) {

    }

    public InvalidInputException(String message, IllegalArgumentException ex) {

    }

    public InvalidInputException(String s, NullPointerException ex) {

    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException ex) {
        String message = ex.getMessage();
        logger.error(message, ex);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<String> handleNullPointerException(NullPointerException ex) {
        String message = ex.getMessage();
        logger.error(message, ex);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
    }

    @ExceptionHandler(InvalidInputException.class)
    public ResponseEntity<String> handleInvalidInputException(InvalidInputException ex) {
        String message = ex.getMessage();
        logger.error(message, ex);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
    }
}