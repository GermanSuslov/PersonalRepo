package ru.ligaintenship.prerevolutionarytinder.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorObject> handleException(UserNotFoundException ex) {
        ErrorObject eObject = new ErrorObject();
        eObject.setStatus(HttpStatus.NOT_FOUND.value());
        eObject.setMessage(ex.getMessage());
        eObject.setTimestamp(System.currentTimeMillis());
        return new ResponseEntity<ErrorObject>(eObject, HttpStatus.NOT_FOUND);
    }
}
