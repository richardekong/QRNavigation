package com.team1.qrnavigationproject.advice;

import com.team1.qrnavigationproject.model.Organization;
import com.team1.qrnavigationproject.model.OrganizationTheme;
import com.team1.qrnavigationproject.model.User;
import com.team1.qrnavigationproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice(annotations = Controller.class)
public class OrganizationThemeControllerAdvice {

    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @ModelAttribute("organizationTheme")
    public OrganizationTheme organizationTheme() {
        OrganizationTheme theme = null;
        Authentication auth = null;
        if (SecurityContextHolder.getContext().getAuthentication() != null) {
            auth = SecurityContextHolder.getContext().getAuthentication();
        }

        if (auth != null) {
            theme = userService.findUserByUsername(auth.getName())
                    .map(User::getOrganization)
                    .map(OrganizationTheme::new)
                    .orElse(new OrganizationTheme(new Organization()));

            System.out.println(auth.getName() + " ===========================================================================");
        }
        return theme;
    }
}
