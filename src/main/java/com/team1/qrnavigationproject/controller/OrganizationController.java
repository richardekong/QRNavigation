package com.team1.qrnavigationproject.controller;

import com.team1.qrnavigationproject.model.Address;
import com.team1.qrnavigationproject.model.Location;
import com.team1.qrnavigationproject.model.Organization;
import com.team1.qrnavigationproject.response.Response;
import com.team1.qrnavigationproject.service.AddressService;
import com.team1.qrnavigationproject.service.LocationService;
import com.team1.qrnavigationproject.service.OrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

@Controller
public class OrganizationController {

    private OrganizationService organizationService;

    private AddressService addressService;

    private LocationService locationService;

    //Todo: This will be resolved as soon as spring security is integrated
    private static final int id = 15;

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

    @GetMapping("/admin/organization/register")
    public String ViewOrganizationRegistrationPage() {
        return "organizationRegistrationPage";
    }


    @GetMapping("/admin/organization/update")
    public ModelAndView ViewOrganizationUpdatePage(ModelAndView modelAndView) {
        //Todo: This will be resolved as soon as spring security is integrated
        var currentOrganization = organizationService.findById(id);
        if (currentOrganization == null) {
            Response response = new Response(
                    HttpStatus.NOT_FOUND.value(),
                    "Failed to retrieve organization details",
                    System.currentTimeMillis()
            );
            modelAndView.addObject("error", response);
            return modelAndView;
        }

        modelAndView.setViewName("organizationUpdatePage");
        modelAndView.addObject("organization", currentOrganization);
        return modelAndView;
    }

    @PostMapping("/admin/organization/register/process")
    public ModelAndView processOrganizationRegistrationForm(
            @Valid @ModelAttribute Organization organization,
            @ModelAttribute Address address,
            @ModelAttribute Location location,
            ModelAndView modelAndView,
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
        Organization savedOrganization = organizationService.save(organization);

        //pass the saved organization to the main admin page, and set the next page to main admin page after submitting the form
        modelAndView.addObject("organization", savedOrganization);
        modelAndView.addObject("success", "%s %s!".formatted(savedOrganization.getName(), HttpStatus.CREATED.getReasonPhrase()));
        modelAndView.setViewName("adminMainPage");
        return modelAndView;
    }

    @PostMapping("/admin/organization/update/process")
    public ModelAndView processUpdateForm(
            @Valid @ModelAttribute Organization orgFormData,
            @ModelAttribute Address addressFormData,
            @ModelAttribute Location locationFormData,
            ModelAndView modelAndView, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            bindingResult.getAllErrors().forEach(error -> logger.log(Level.SEVERE, error.getDefaultMessage()));
            modelAndView.addObject("error", HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
        }

        //updating organization detail
        //Todo: This will be resolved as soon as spring security is integrated
        Organization orgToUpdate = organizationService.findById(id);

        //update the location data
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
        modelAndView.setViewName("adminMainPage");
        return modelAndView;
    }

}
