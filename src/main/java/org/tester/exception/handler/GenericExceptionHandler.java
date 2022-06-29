package org.tester.exception.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.tester.response.ResponseHandler;

import java.net.ConnectException;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GenericExceptionHandler {

    @ExceptionHandler(ConnectException.class)
    public ResponseEntity<?> handle(ConnectException conn) {
        return new ResponseEntity<>(ResponseHandler.prepareResponse(
                "Server might be down please try after sometime", 503), HttpStatus.SERVICE_UNAVAILABLE);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handle(Exception conn) {
        return new ResponseEntity<>(ResponseHandler.prepareResponse(
                "Internal Server Error", 500), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
