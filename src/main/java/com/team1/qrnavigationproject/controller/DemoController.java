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

    @GetMapping("/admin/contents")
    public String ViewContentManagementPage() {
        return "contentManagementPage";
    }

    @GetMapping("/admin/contents/createContent")
    public String ViewCreateContentPage() {
        return "createContentPage";
    }

    @GetMapping("/admin/contents/updateContent")
    public String ViewUpdateContentPage() {
        return "contentUpdate";
    }

    @GetMapping("/qrnavigation/footer")
    public String ViewQRNavigationFooter() {
        return "qrNavigationFooterTest";
    }

    @GetMapping("/qrnavigation/header")
    public String ViewQRNavigationHeader() {
        return "qrNavHeaderTest";
    }


    @GetMapping("/qrnavigation/login")
    public String viewLoginPage() {
        return "loginpage";
    }

    @GetMapping("/qrnavigation/signup")
    public String viewSignUpPage() {
        return "signUpPage";
    }

}
