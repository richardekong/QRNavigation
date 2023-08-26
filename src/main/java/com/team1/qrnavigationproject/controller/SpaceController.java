package com.team1.qrnavigationproject.controller;

import com.team1.qrnavigationproject.model.*;
import com.team1.qrnavigationproject.response.CustomException;
import com.team1.qrnavigationproject.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

import static com.team1.qrnavigationproject.configuration.QRNavigationPaths.*;
import static java.lang.String.format;

@Controller
public class SpaceController {
    private UserService userService;

    private SpaceService spaceService;

    private AddressService addressService;

    private LocationService locationService;

    private OrganizationService organizationService;

    private SpaceTypeService spaceTypeService;

    @Autowired
    public void setSpaceType(SpaceTypeService spaceTypeService) {
        this.spaceTypeService = spaceTypeService;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setOrganizationService(OrganizationService organizationService) {
        this.organizationService = organizationService;
    }

    @Autowired
    public void setSpaceService(SpaceService spaceService) {
        this.spaceService = spaceService;
    }

    @Autowired
    public void setAddressService(AddressService addressService) {
        this.addressService = addressService;
    }

    @Autowired
    public SpaceController(SpaceService spaceService) {
        this.spaceService = spaceService;
    }


    @Autowired
    public void setLocationService(LocationService locationService) {
        this.locationService = locationService;
    }

    @GetMapping(ADMIN_SPACES_PAGE)
    public String getAllSpaces(Model model, RedirectAttributes attributes, Authentication auth) {
        if (auth.getName() == null) {
            attributes.addFlashAttribute("error", "Error (%d): %s"
                    .formatted(
                            HttpStatus.UNAUTHORIZED.value(),
                            HttpStatus.UNAUTHORIZED.getReasonPhrase())
            );
            return "redirect:/login";
        }

        userService.findUserByUsername(auth.getName()).map(User::getOrganization)
                .map(Organization::getId)
                .ifPresentOrElse(organizationId -> {
                            List<Space> spaces = spaceService.getAllSpaces()
                                    .stream()
                                    .filter(space -> space.getOrganization().getId() == organizationId)
                                    .toList();
                            List<List<SubSpace>> groupedOfSubspaces = spaces
                                    .stream()
                                    .map(Space::getSubSpaces)
                                    .toList();
                            model.addAttribute("spaces", spaces);
                            model.addAttribute("groupedSpaces", groupedOfSubspaces);
                        }, () ->
                                attributes.addFlashAttribute("warning", "No Spaces has been registered")
                );

        return "managePlaces";

    }

    @GetMapping(ADMIN_SPACES_CREATE)
    public ModelAndView viewCreateMainPlacePage(ModelAndView mav) {
        List<SpaceType> spaceTypes = spaceTypeService.getAllSpaceTypes();
        mav.addObject("spaceTypes", spaceTypes);
        mav.setViewName("createMainPlace");
        return mav;
    }


    @PostMapping(ADMIN_SPACES_CREATE_PROCESS)
    public ModelAndView createMainPlaceForm(
            @Valid @ModelAttribute Space space,
            @Valid @ModelAttribute Address address,
            @ModelAttribute Location location,
            ModelAndView mav,
            Authentication auth) {

        Optional<User> admin = Optional.empty();
        if (auth.isAuthenticated() && auth.getName() != null) {
            admin = userService.findUserByUsername(auth.getName());
        }
        admin.ifPresentOrElse(user -> {
            Organization organization = user.getOrganization();
            Location savedLocation = locationService.saveLocation(location);
            address.setLocation(savedLocation);
            Address savedAddress = addressService.saveAddress(address);
            organization.setAddress(savedAddress);
            organization = organizationService.update(organization);
            space.setOrganization(organization);
            space.setAddressId(savedAddress.getId());
            Space savedSpace = spaceService.saveSpace(space);
            HttpStatus status = HttpStatus.CREATED;
            mav.addObject("space", savedSpace)
                    .addObject("success", "Success (%d): Space created".formatted(status.value()));

        }, () -> mav.addObject("error", HttpStatus.UNAUTHORIZED.getReasonPhrase())
                .setViewName("redirect:/" + LOGIN));
        mav.setViewName("redirect:/admin/places");
        return mav;
    }


    @GetMapping("/admin/places/update/{id}")
    public String viewPlacePage(@PathVariable Integer id, Model mav, RedirectAttributes attributes) {
        spaceService.getSpaceById(id).ifPresentOrElse(space -> mav.addAttribute("space", space),
                () -> attributes.addFlashAttribute("error", "Error (%d): Space not found".formatted(HttpStatus.NOT_FOUND.value())));
        return "placeUpdates";
    }

    @PostMapping("/admin/spaces/update/process")
    public ModelAndView editPlaceForm(
            @Valid @ModelAttribute("space") Space space, ModelAndView mav) {
        spaceService.updateSpace(space);
        mav.addObject("success", "Changes saved!");
        mav.setViewName("redirect:/admin/places");
        return mav;
    }

    @RequestMapping(value = "/admin/spaces/delete/{id}", method = {RequestMethod.DELETE, RequestMethod.GET})
    public String deleteSpace(@PathVariable int id) {
        spaceService.deleteSpace(id);
        return "redirect:/admin/places";
    }

    @GetMapping("/admin/places/create/subspace")
    public ModelAndView viewCreateSubPlacePage(ModelAndView mav, Authentication auth) {
        Optional<User> admin = Optional.empty();
        if (auth != null && auth.getName() != null && auth.isAuthenticated()) {
            //get current admin
            admin = userService.findUserByUsername(auth.getName());
        }
        admin.ifPresentOrElse(user -> {
            List<Space> spacesFromOrganization = spaceService
                    .getAllSpaces()
                    .stream()
                    .filter(space -> space.getOrganization() == user.getOrganization())
                    .toList();
            List<SpaceType> spaceTypes = spaceTypeService.getAllSpaceTypes();
            mav.addObject("spaces", spacesFromOrganization);
            mav.addObject("spaceTypes", spaceTypes);
            mav.setViewName("createSubPlace");
        }, () -> {
            mav.addObject("error", format("Error (%d): unauthorized", HttpStatus.UNAUTHORIZED.value()));
            mav.setViewName("redirect:" + LOGIN);
        });
        return mav;
    }

    @PostMapping("/admin/spaces/create/subspace/process")
    public ModelAndView createSubPlaceForm(
            @RequestParam(name = "spaceId") String spaceIdParam,
            @RequestParam(name = "typeId") String typeIdParam,
            @Valid @ModelAttribute SubSpace subSpace,
            ModelAndView mav,
            RedirectAttributes redirectAttributes,
            Authentication auth) {

        int spaceId = Integer.parseInt(spaceIdParam),
                typeId = Integer.parseInt(typeIdParam);

        if (spaceId < 1) {
            redirectAttributes.addAttribute("error", "Error (%d): select a space".formatted(HttpStatus.BAD_REQUEST.value()));
            mav.setViewName("redirect:/admin/places/create/subspace");
            return mav;
        }
        if (typeId < 1) {
            redirectAttributes.addAttribute("error", "Error (%d): select a space type".formatted(HttpStatus.BAD_REQUEST.value()));
            mav.setViewName("redirect:/admin/places/create/subspace");
            return mav;
        }

        Optional<User> admin = Optional.empty();
        Optional<Space> optionalSpace = spaceService.getSpaceById(spaceId);
        Optional<SpaceType> optionalSpaceType = spaceTypeService.getSpaceTypeById(typeId);
        if (auth.isAuthenticated() && auth.getName() != null) {
            admin = userService.findUserByUsername(auth.getName());
        }
        if (optionalSpace.isEmpty()) {
            throw new CustomException("Could not find space", HttpStatus.NOT_FOUND);
        }
        if (optionalSpaceType.isEmpty()) {
            throw new CustomException("Could not find space type", HttpStatus.NOT_FOUND);
        }
        admin.ifPresentOrElse(user -> {
            Space space;
            SpaceType spaceType;
            Organization organization = user.getOrganization();
            space = optionalSpace.get();
            spaceType = optionalSpaceType.get();
            subSpace.setTypeId(spaceType.getId());
            space.add(subSpace);
            Space updatedSpace = spaceService.updateSpace(space);
            organization.add(updatedSpace);
            organizationService.update(organization);
            mav.addObject("space", updatedSpace)
                    .addObject("success", "Success (%d): Space created".formatted(HttpStatus.CREATED.value()));
        }, () -> {
            mav.addObject("error", HttpStatus.UNAUTHORIZED.getReasonPhrase());
            mav.setViewName("redirect:/" + LOGIN);
        });
        mav.setViewName("redirect:/admin/places");
        return mav;
    }


}

