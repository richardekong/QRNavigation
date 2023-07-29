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
    public String viewContentPage() {
        return "contentPage";
    }

    @GetMapping("/admin/qrcodes")
    public String viewQRCodes(){
        return "qrcodes";
    }
    @GetMapping("/admin/qrcodes/generate")
    public String viewQRCodeGenerationPage(){
        return "qrcodeGeneration";
    }
    @GetMapping("/admin/qrcodes/update")
    public String viewQRCodeUpdatePage(){
        return "qrcodeUpdate";
    }
    @GetMapping("/admin/main")
    public String viewAdminMainPage(){
        return "adminMainPage";
    }

}
