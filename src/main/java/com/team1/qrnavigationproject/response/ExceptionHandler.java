package com.team1.qrnavigationproject.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@ControllerAdvice
public class ExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler
    public ResponseEntity<Response> handleException(CustomException ce) {
        Response res = new Response();
        return createErrorResponse(res, ce.getStatus(), ce.getMessage());
    }

    @org.springframework.web.bind.annotation.ExceptionHandler
    public ResponseEntity<Response> handleException(NumberFormatException ne) {
        Response res = new Response();
        return createErrorResponse(res, HttpStatus.BAD_REQUEST, ne.getMessage());
    }

    @org.springframework.web.bind.annotation.ExceptionHandler
    public ResponseEntity<Response> handleException(MethodArgumentNotValidException ex) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        Response res = new Response();
        StringBuilder errorMessage = new StringBuilder();
        List<ObjectError> errors = ex.getAllErrors();
        errors.forEach(error -> {
            int errorIndex = errors.indexOf(error);
            int lastErrorIndex = errors.size() - 1;
            String defaultErrorMessage = error.getDefaultMessage();
            errorMessage.append(errorIndex < lastErrorIndex ? defaultErrorMessage + ",\u0020and\u0020" : defaultErrorMessage);
        });
        return createErrorResponse(res, status, errorMessage.toString());
    }


    private ResponseEntity<Response> createErrorResponse(Response res, HttpStatus status, String message) {
        res.setStatus(status.value());
        res.setMessage(message);
        res.setTime(System.currentTimeMillis());
        return new ResponseEntity<>(res, status);
    }

}

