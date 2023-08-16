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

}
