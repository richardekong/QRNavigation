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

    @GetMapping("/admin/main")
    public String viewAdminMainPage(){
        return "adminMainPage";
    }

    @GetMapping("/managePlaces")
    public String viewManagePlacePage() {
        return "managePlaces";
    }

    @GetMapping("/editTextManagePlaces")
    public String viewEditPlacesPage() {return "editTextManagePlaces";
    }
    @GetMapping("/createSubPlace")
    public String viewCreatePlacesPage() {
        return "createSubPlace";
    }

    @GetMapping("/createMainPlace")
    public String viewCreateMainPlacePage() {
        return "createMainPlace";
    }

}
