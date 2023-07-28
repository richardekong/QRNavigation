package com.example.qrnavigationproject.controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class DemoController {
    @RequestMapping("/home")
    public String showHomePage() {
        return "landingPage";
    }

    @GetMapping("/content")
    public String ViewContentPage() {
        return "contentPage";
    }

    @GetMapping("/admin/events")
    public String ViewEventManagementPage() {
        return "eventManagementPage";
    }

    @GetMapping("/admin/events/CreateEvent")
    public String ViewCreateEventPage() {
        return "CreateEventPage";
    }

    @GetMapping("/admin/events/updateEvent")
    public String ViewUpdateEventPage() {
        return "eventUpdate";
    }

    @GetMapping("/admin/main")
    public String viewAdminMainPage(){
        return "adminMainPage";
    }

}
