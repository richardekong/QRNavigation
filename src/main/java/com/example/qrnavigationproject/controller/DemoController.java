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

    @GetMapping("/admin/organization/register")
    public String ViewOrganizationRegistrationPage() {
        return "organizationRegistrationPage";
    }
    @GetMapping("/admin/organization/update")
    public String ViewOrganizationUpdatePage() {
        return "organizationUpdatePage";
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

    @GetMapping("admin/places")
    public String viewManagePlacePage() {
        return "managePlaces";
    }

    @GetMapping("admin/places/updates")
    public String viewEditPlacesPage() {return "placeUpdates";
    }
    @GetMapping("admin/subPlace/createSubPlace")
    public String viewCreatePlacesPage() {
        return "createSubPlace";
    }

    @GetMapping("admin/places/createPlace")
    public String viewCreateMainPlacePage() {
        return "createMainPlace";
    }

    @GetMapping("/admin/events")
    public String ViewEventManagementPage() {
        return "eventManagementPage";
    }

    @GetMapping("/admin/events/createEvent")
    public String ViewCreateEventPage() {
        return "createEventPage";
    }

    @GetMapping("/admin/events/updateEvent")
    public String ViewUpdateEventPage() {
        return "eventUpdate";
    }

}
