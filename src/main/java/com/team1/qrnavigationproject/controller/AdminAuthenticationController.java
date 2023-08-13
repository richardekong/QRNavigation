package com.team1.qrnavigationproject.controller;


import com.team1.qrnavigationproject.model.Role;
import com.team1.qrnavigationproject.model.User;
import com.team1.qrnavigationproject.model.UserType;
import com.team1.qrnavigationproject.response.CustomException;
import com.team1.qrnavigationproject.response.Response;
import com.team1.qrnavigationproject.service.RoleService;
import com.team1.qrnavigationproject.service.UserService;
import com.team1.qrnavigationproject.service.UserTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

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

    @GetMapping("/qrnavigation/signup")
    public String viewSignUpPage() {
        return "signUpPage";
    }


    @PostMapping(AUTH_SIGN_UP)
    public String requestSignUpPage(@ModelAttribute @Valid User userFormData, Model model) {
        UserType userType = userTypeService.save(new UserType("Adult"));
        Role userRole = roleService.save(new Role(ADMIN.name()));
        User savedUser;
        try {
            savedUser = userService.save(
                    userFormData,
                    userType,
                    userRole);
            model.addAttribute("user", savedUser);
        } catch (CustomException e) {
            model.addAttribute("error", e.getMessage());
            throw new CustomException(
                    e.getMessage(),
                    e.getStatus()
            );
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String getLoginPage(Model model) {
        try {
            return "loginPage";
        } catch (BadCredentialsException bce) {
            model.addAttribute("error",
                    new Response(HttpStatus.UNAUTHORIZED.value(),
                            bce.getMessage(),
                            System.currentTimeMillis())
            );
            return "loginPage";
        }
    }


    @GetMapping("/login-error")
    public String loginError(Model model) {
        // login failed
        model.addAttribute("error",
                new Response(HttpStatus.UNAUTHORIZED.value(), "Wrong username or password", System.currentTimeMillis()));
        return "loginPage";
    }


}
