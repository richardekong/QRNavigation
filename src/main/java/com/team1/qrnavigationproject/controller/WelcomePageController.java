package com.team1.qrnavigationproject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class WelcomePageController {

    @GetMapping("/qrnavigation/welcome")
    public String showWelcomePage() {
        return "welcomePage";
    }

}
