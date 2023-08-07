package com.team1.qrnavigationproject.controller;

import com.team1.qrnavigationproject.model.Organization;
import com.team1.qrnavigationproject.service.OrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class OrganizationController {

    private OrganizationService organizationService;

    @Autowired
    private void setOrganizationService(OrganizationService organizationService){
        this.organizationService = organizationService;
    }

    @GetMapping("/admin/organization/register")
    public String ViewOrganizationRegistrationPage() {
        return "organizationRegistrationPage";
    }
    @GetMapping("/admin/organization/update")
    public String ViewOrganizationUpdatePage() {
        return "organizationUpdatePage";
    }

}
