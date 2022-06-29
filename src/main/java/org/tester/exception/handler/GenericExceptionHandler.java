package org.tester.exception.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.tester.response.ResponseHandler;

import java.net.ConnectException;

@ControllerAdvice
public class GenericExceptionHandler {

    static final Logger logger = LoggerFactory.getLogger(GenericExceptionHandler.class);

    @ExceptionHandler(ConnectException.class)
    public ResponseEntity<?> handle(ConnectException conn) {
        logger.error("connection refused", conn);
        return new ResponseEntity<>(ResponseHandler.prepareResponse(
                "Server might be down please try after sometime", 503), HttpStatus.SERVICE_UNAVAILABLE);
    }

    @ExceptionHandler(Throwable.class)
    public ResponseEntity<?> handle(Throwable exception) {
        logger.error("exception", exception);
        return new ResponseEntity<>(ResponseHandler.prepareResponse(
                "Internal Server Error", 500), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
