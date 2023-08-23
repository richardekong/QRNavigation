package com.team1.qrnavigationproject.controller;


import com.team1.qrnavigationproject.model.Location;
import com.team1.qrnavigationproject.model.OrganizationTheme;
import com.team1.qrnavigationproject.model.Space;
import com.team1.qrnavigationproject.model.SubSpace;
import com.team1.qrnavigationproject.service.LocationService;
import com.team1.qrnavigationproject.service.OrganizationService;
import com.team1.qrnavigationproject.service.QRCodeService;
import com.team1.qrnavigationproject.service.SpaceService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriTemplate;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

import static com.team1.qrnavigationproject.configuration.QRNavigationPaths.QRCODE_PAGE_URL;

@Controller
public class LandingPageController {

    private LocationService locationService;

    private SpaceService spaceService;

    private OrganizationService orgService;

    private QRCodeService qrCodeService;


    @GetMapping("/{organizationName}/qrnavigation/home")
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
                    //Get list of group of subspaces for a given space
                    List<List<SubSpace>> groupOfManagedSpaces = managedSpaces
                            .stream()
                            .map(Space::getSubSpaces)
                            .toList();
                    //Get list of locations for an organization's managed spaces
                    List<Location> locations = locationService.getAllLocations()
                            .stream()
                            .filter(loc -> {
                                int orgId0 = loc.getAddress()
                                        .getOrganization()
                                        .getId();
                                int orgId1 = organization.getId();
                                return orgId0 == orgId1;
                            }).toList();
                    //pass the organization's theme together with these lists to the view/page
                    model
                            .addAttribute("theme", theme)
                            .addAttribute("spaces", managedSpaces)
                            .addAttribute("groupedSubspaces", groupOfManagedSpaces)
                            .addAttribute("locations", locations);
                }, () ->
                        model.addAttribute("error", "Error (%d): %s %s".formatted(
                                HttpStatus.NOT_FOUND.value(),
                                organizationName,
                                HttpStatus.NOT_FOUND.getReasonPhrase())
                        ));

        return page;
    }

    @GetMapping("/{organizationName}/qrnarvigation/content")
    public String viewContentPage(
            @PathVariable String organizationName,
            @Valid @ModelAttribute LandingPageFormData formData,
            RedirectAttributes redirectAttributes) {
        //redirect the user to content page
        AtomicReference<String> contentPageRedirect = new AtomicReference<>("");
        //Get desired values from request parameters
        spaceService.findSpaceByName(formData.selectedSpaceName).ifPresentOrElse(space -> {
            SubSpace subSpace = space
                    .getSubSpaces()
                    .stream()
                    .filter(sb -> sb.getName().equals(formData.selectedSubspaceName))
                    .findFirst()
                    .orElse(null);
            UriTemplate template = new UriTemplate(QRCODE_PAGE_URL);
            if (subSpace != null) {
                Map<String, String> uriVariables = Map.of(
                        "organization", organizationName,
                        "space", space.getName(),
                        "subspace", subSpace.getName()
                );
                String path = template.expand(uriVariables).toString();
                contentPageRedirect.set("redirect:%s".formatted(path));
            }else {
                HttpStatus status = HttpStatus.NOT_FOUND;
                redirectAttributes.addFlashAttribute("error", "Error (%d): Subspace not found".formatted(status.value()));
                contentPageRedirect.set("redirect:/%s/qrnavigation/content".formatted(organizationName));
            }
        }, () -> {
            HttpStatus status = HttpStatus.NOT_FOUND;
            redirectAttributes.addFlashAttribute("error", "Error (%d): Space not found".formatted(status.value()));
            contentPageRedirect.set("redirect:/%s/qrnavigation/content".formatted(organizationName));

        });
        return contentPageRedirect.get();
    }

    public record LandingPageFormData(
            @NotBlank(message = "Pick a space")
            String selectedSpaceName,
            @NotBlank(message = "Pick a Subspace")
            String selectedSubspaceName) {
    }


}
