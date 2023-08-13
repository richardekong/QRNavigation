package com.team1.qrnavigationproject.model;


import lombok.Getter;

@Getter
public class OrganizationTheme {
    final String name;
    final String logoURL;
    final String headerBackground;
    final String footerBackground;
    final String websiteURL;

    public OrganizationTheme(Organization organization) {
        this.name = organization.getName();
        this.logoURL = organization.getLogoURL();
        this.headerBackground = organization.getHeaderBackground();
        this.footerBackground = organization.getFooterBackground();
        this.websiteURL = organization.getWebsiteURL();
    }

}