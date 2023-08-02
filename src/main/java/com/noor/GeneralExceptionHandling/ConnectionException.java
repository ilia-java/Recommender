package com.noor.GeneralExceptionHandling;

import com.mongodb.DuplicateKeyException;
import com.mongodb.MongoException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ConnectionException extends RuntimeException {
    public ConnectionException() {
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(ConnectionException.class);

    public ConnectionException(String message, Throwable cause) {
        super(message, cause);
    }


    public ConnectionException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    @ExceptionHandler(MongoException.class)
    public ResponseEntity<String> handleMongoDBExceptions(MongoException ex) {
        LOGGER.error("Error occurred while accessing MongoDB.", ex);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("An error occurred while accessing MongoDB.");
    }//ارور پونصد نده

    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<String> handleDataAccessException(DataAccessException ex) {
        LOGGER.error("Error occurred while accessing the data.", ex);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("An error occurred while accessing the data.");
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleExceptions(Exception ex) {
        LOGGER.error("Internal server error occurred.");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("An internal server error occurred.");
    }

    @ExceptionHandler(NumberFormatException.class)
    public ResponseEntity<String> handleNumberFormatException(NumberFormatException ex) {
        String message = "Invalid number format";
        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(DuplicateKeyException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public String handleDuplicateKeyException(DuplicateKeyException ex) {

        return "This option has already been saved";
    }

}
