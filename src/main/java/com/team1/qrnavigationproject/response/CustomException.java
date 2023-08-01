package com.team1.qrnavigationproject.response;

import com.sun.istack.NotNull;
import org.springframework.http.HttpStatus;


public class CustomException extends RuntimeException{

    private HttpStatus status;

    public CustomException(String message, @NotNull HttpStatus status){
        super(message);
        this.status = status;
    }

    public CustomException(@NotNull HttpStatus status){
        super(status.getReasonPhrase());
        this.status = status;
    }

    public void setStatus(HttpStatus status){
        this.status = status;
    }
    public HttpStatus getStatus() {
        return status;
    }
}

