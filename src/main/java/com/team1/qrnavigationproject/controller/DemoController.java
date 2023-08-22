package com.team1.qrnavigationproject.controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class DemoController {


    @RequestMapping("/qrnavigation/welcome")
    public String showWelcomePage() {
        return "welcomePage";
    }

    @RequestMapping("/home")
    public String showHomePage() {
        return "landingPage";
    }
    @GetMapping("/content")
    public String viewContentPage() {
        return "contentPage";
    }

    @GetMapping("/admin/main")
    public String viewAdminMainPage(){
        return "adminMainPage";
    }



    @GetMapping("/qrnavigation/footer")
    public String ViewQRNavigationFooter() {
        return "qrNavigationFooterTest";
    }

    @GetMapping("/qrnavigation/header")
    public String ViewQRNavigationHeader() {
        return "qrNavHeaderTest";
    }

}
