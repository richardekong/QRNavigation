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

    @GetMapping("/admin/organization/register")
    public String ViewOrganizationRegistrationPage() {
        return "organizationRegistrationPage";
    }
    @GetMapping("/admin/organization/update")
    public String ViewOrganizationUpdatePage() {
        return "organizationUpdatePage";
    }
    @GetMapping("/admin/main")
    public String viewAdminMainPage(){
        return "adminMainPage";

    }

}
