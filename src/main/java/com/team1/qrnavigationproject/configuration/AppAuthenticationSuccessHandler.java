package com.team1.qrnavigationproject.configuration;

import com.team1.qrnavigationproject.model.Organization;
import com.team1.qrnavigationproject.model.User;
import com.team1.qrnavigationproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
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


        if (authentication != null && authentication.isAuthenticated()) {
            //get the current admin
            var optionalUser = userService.findUserByUsername(authentication.getName());
            //check if the admin has registered an organization
            if (optionalUser.isPresent()){
                var currentUser = optionalUser.get();
                if (currentUser.getOrganization() != null) {
                    //redirect the user to the admin main page
                    response.sendRedirect("/admin/main");
                } else {
                    //redirect the admin to the organization Registration Page
                    response.sendRedirect("/admin/organization/register");
                }
            }
        }else {
            throw new BadCredentialsException("Wrong username or password");
        }
    }


}
