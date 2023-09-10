package com.team1.qrnavigationproject.controller;

import com.team1.qrnavigationproject.configuration.AuthenticatedUser;
import com.team1.qrnavigationproject.configuration.QRNavigationPaths;
import com.team1.qrnavigationproject.model.*;
import com.team1.qrnavigationproject.response.CustomException;
import com.team1.qrnavigationproject.service.AddressService;
import com.team1.qrnavigationproject.service.LocationService;
import com.team1.qrnavigationproject.service.OrganizationService;
import com.team1.qrnavigationproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.team1.qrnavigationproject.configuration.AuthenticatedUser.requestCurrentUser;

@Controller
public class OrganizationController {

    private OrganizationService organizationService;

    private AddressService addressService;

    private LocationService locationService;

    private UserService userService;

    private final Logger logger = Logger.getLogger(this.getClass().getSimpleName());

    @Autowired
    private void setOrganizationService(OrganizationService organizationService) {
        this.organizationService = organizationService;
    }

    @Autowired
    private void setLocationService(LocationService locationService) {
        this.locationService = locationService;
    }

    @Autowired
    private void setAddressService(AddressService addressService) {
        this.addressService = addressService;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/admin/organization/register")
    public ModelAndView ViewOrganizationRegistrationPage(Authentication auth, ModelAndView modelAndView) {
        modelAndView.setViewName("organizationRegistrationPage");
        return modelAndView;
    }


    @GetMapping("/admin/organization/update")
    public ModelAndView ViewOrganizationUpdatePage(Authentication auth, ModelAndView modelAndView) {
        Organization currentOrganization = new Organization();
        if (auth != null) {
            currentOrganization = requestCurrentUser(auth, userService).getOrganization();
        }
        modelAndView.setViewName("organizationUpdatePage");
        modelAndView.addObject("organization", currentOrganization);
        return modelAndView;
    }

    @PostMapping(QRNavigationPaths.ADMIN_ORG_REG_PROCESS)
    public ModelAndView processOrganizationRegistrationForm(
            @Valid @ModelAttribute Organization organization,
            @ModelAttribute Address address,
            @ModelAttribute Location location,
            ModelAndView modelAndView,
            RedirectAttributes redirectAttributes,
            Authentication auth,
            BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            bindingResult.getAllErrors().forEach(error -> logger.log(Level.SEVERE, error.getDefaultMessage()));
            modelAndView.addObject("error", HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
            return modelAndView;
        }
        //first save location to the database
        Location savedLocation = locationService.saveLocation(location);
        //associate the address with  the saved location, then save the address
        address.setLocation(savedLocation);
        Address savedAddress = addressService.saveAddress(address);
        //associate the organization with the saved address then save the organization
        organization.setAddress(savedAddress);
        //associate the organization with the current user
        requestCurrentUser(auth, userService).setOrganization(organization);

        try {
            Organization savedOrganization = organizationService.save(organization);
            //pass the saved organization to the main admin page, and set the next page to main admin page after submitting the form
            modelAndView.addObject("organization", savedOrganization);
            modelAndView.addObject("success", "%s %s!".formatted(savedOrganization.getName(), HttpStatus.CREATED.getReasonPhrase()));
            modelAndView.setViewName("redirect:/admin/main");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", "Error (%d): %s".formatted(
                    HttpStatus.CONFLICT.value(),
                    e.getMessage()
            ));
            modelAndView.setViewName("redirect:/admin/organization/register");
        }
        return modelAndView;
    }

    @PostMapping(QRNavigationPaths.ADMIN_ORG_UPDATE_PROCESS)
    public ModelAndView processUpdateForm(
            @Valid @ModelAttribute Organization orgFormData,
            @ModelAttribute Address addressFormData,
            @ModelAttribute Location locationFormData,
            Authentication auth,
            ModelAndView modelAndView, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            bindingResult.getAllErrors().forEach(error -> logger.log(Level.SEVERE, error.getDefaultMessage()));
            modelAndView.addObject("error", HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
        }

        //updating organization detail
        Organization orgToUpdate = requestCurrentUser(auth, userService).getOrganization();
        Location updatedLocation = locationService.saveLocation(locationFormData);
        //update the address
        addressFormData.setLocation(updatedLocation);
        Address updatedAddress = addressService.saveAddress(addressFormData);

        //update the organization detail
        orgFormData.setId(orgToUpdate.getId());
        orgFormData.setAddress(updatedAddress);
        orgToUpdate = organizationService.update(orgFormData);

        modelAndView.addObject("organization", orgToUpdate);
        modelAndView.addObject("success", "Changes saved!");
        modelAndView.setViewName("redirect:/admin/main");
        return modelAndView;
    }

    @GetMapping("/admin/organization")
    public ResponseEntity<Organization> requestOrganization(Authentication auth) {
        return Optional.of(requestCurrentUser(auth, userService).getOrganization())
                .map(org -> new ResponseEntity<>(org, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }


}
