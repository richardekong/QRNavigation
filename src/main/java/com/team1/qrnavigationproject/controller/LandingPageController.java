package com.team1.qrnavigationproject.controller;


import com.team1.qrnavigationproject.model.Location;
import com.team1.qrnavigationproject.model.OrganizationTheme;
import com.team1.qrnavigationproject.model.Space;
import com.team1.qrnavigationproject.service.LocationService;
import com.team1.qrnavigationproject.service.OrganizationService;
import com.team1.qrnavigationproject.service.SpaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

import static com.team1.qrnavigationproject.configuration.QRNavigationPaths.QRNAVIGATION_LANDING_PAGE;

@Controller
public class LandingPageController {
    private LocationService locationService;

    private SpaceService spaceService;

    private OrganizationService orgService;

    @Autowired
    public void setLocationService(LocationService locationService) {
        this.locationService = locationService;
    }

    @Autowired
    public void setSpaceService(SpaceService spaceService) {
        this.spaceService = spaceService;
    }

    @Autowired
    public void setOrgService(OrganizationService orgService) {
        this.orgService = orgService;
    }

    @GetMapping(QRNAVIGATION_LANDING_PAGE)
    public String showLandingPage(
            @PathVariable String organizationName,
            Model model,
            RedirectAttributes redirectAttributes) {
        String page = "landingPage";
        Optional.of(orgService.findByName(organizationName))
                .ifPresentOrElse(organization -> {
                    //Get the organization's theme
                    OrganizationTheme theme = new OrganizationTheme(organization);
                    //Get list of organization managed spaces
                    List<Space> managedSpaces = spaceService.getAllSpaces()
                            .stream()
                            .filter(space -> space.getOrganization().getId() == organization.getId())
                            .toList();

                    //Get list of locations for an organization or an empty list
                    List<Location> locations = locationService.findLocationByOrganizationId(organization.getId());
                    //pass the organization's theme together with these lists to the view/page
                    model
                            .addAttribute("organizationName", organizationName)
                            .addAttribute("organizationTheme", theme)
                            .addAttribute("spaces", managedSpaces)
                            .addAttribute("locations", locations);
                }, () ->
                        redirectAttributes.addFlashAttribute("error", "Error (%d): %s %s".formatted(
                                HttpStatus.NOT_FOUND.value(),
                                organizationName,
                                HttpStatus.NOT_FOUND.getReasonPhrase())
                        ));

        return page;
    }

}

