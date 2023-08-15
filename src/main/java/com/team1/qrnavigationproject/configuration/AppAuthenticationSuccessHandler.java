package com.team1.qrnavigationproject.configuration;

import com.team1.qrnavigationproject.model.Organization;
import com.team1.qrnavigationproject.model.User;
import com.team1.qrnavigationproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class AppAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final UserService userService;

    @Autowired
    public AppAuthenticationSuccessHandler(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {

        //get the current admin
        var currentUser = new User();
        if (authentication != null && authentication.isAuthenticated()) {
            currentUser = userService.findUserByUsername(authentication.getName())
                    .orElseThrow(() -> new UsernameNotFoundException("UnAuthorized"));
        }
        //check if the admin has registered an organization
        if (currentUser.getOrganization() != null) {
            Organization organization = currentUser.getOrganization();
            //redirect the user to the admin main page
            response.sendRedirect("/admin/main");
        } else {
            //redirect the admin to the organization Registration Page
            response.sendRedirect("/admin/organization/register");
        }
    }

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain chain,
            Authentication auth) throws IOException, ServletException {

        AuthenticationSuccessHandler.super.onAuthenticationSuccess(request, response, chain, auth);
    }
}
