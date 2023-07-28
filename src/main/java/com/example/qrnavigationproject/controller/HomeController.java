package com.example.qrnavigationproject.controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class HomeController {
    @RequestMapping("/home")
    public String showHomePage() {
        return "landingPage";
    }

    @GetMapping("/content")
    public String ViewContentPage() {
        return "contentPage";
    }

    @GetMapping("/admin/eventManagement")
    public String ViewEventManagementPage() {
        return "eventManagementPage";
    }

    @GetMapping("/admin/CreateEvent")
    public String ViewCreateEventPage() {
        return "CreateEventPage";
    }

}
