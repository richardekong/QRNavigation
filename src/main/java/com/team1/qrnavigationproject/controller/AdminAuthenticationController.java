package com.team1.qrnavigationproject.controller;


import com.team1.qrnavigationproject.model.Role;
import com.team1.qrnavigationproject.model.User;
import com.team1.qrnavigationproject.model.UserType;
import com.team1.qrnavigationproject.service.RoleService;
import com.team1.qrnavigationproject.service.UserService;
import com.team1.qrnavigationproject.service.UserTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

import static com.team1.qrnavigationproject.configuration.QRNavigationPaths.AUTH_LOGIN;
import static com.team1.qrnavigationproject.configuration.QRNavigationPaths.AUTH_SIGN_UP;
import static com.team1.qrnavigationproject.model.Role.RoleType.ADMIN;

@Controller
public class AdminAuthenticationController {

    private UserService userService;

    private UserTypeService userTypeService;

    private RoleService roleService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setUserTypeService(UserTypeService userTypeService) {
        this.userTypeService = userTypeService;
    }

    @Autowired
    public void setRoleService(RoleService roleService) {
        this.roleService = roleService;
    }

    @PostMapping(AUTH_SIGN_UP)
    @ResponseStatus(HttpStatus.OK)
    public ModelAndView requestSignUpPage(@ModelAttribute @Valid User userFormData, ModelAndView modelAndView) {
        UserType userType = userTypeService.save(new UserType("Adult"));
        Role userRole = roleService.save(new Role(ADMIN.name()));
        User savedUser = userService.save(
                userFormData,
                userType,
                userRole);
        modelAndView.addObject("user",savedUser);
        modelAndView.setViewName("organizationRegistrationPage");
        return modelAndView;
    }

    @PostMapping(AUTH_LOGIN)
    public String requestLoginPage(Model model) {


        return "loginpage";
    }




}
