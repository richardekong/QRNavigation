package com.team1.qrnavigationproject.advice;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class PostCodeAdvice {

    @Value("${postcode.api.key}")
    private String APIKey;

    @ModelAttribute("postcodeAPI")
    public String postcodeAPIKey(){
        return APIKey;
    }
}
