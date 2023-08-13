package com.team1.qrnavigationproject.fullContainerTest;


import com.team1.qrnavigationproject.model.Role;
import com.team1.qrnavigationproject.model.User;
import com.team1.qrnavigationproject.model.UserType;
import com.team1.qrnavigationproject.repository.RoleRepo;
import com.team1.qrnavigationproject.repository.UserRepo;
import com.team1.qrnavigationproject.repository.UserTypeRepo;
import com.team1.qrnavigationproject.stub.TestData;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static com.team1.qrnavigationproject.configuration.QRNavigationPaths.AUTH_SIGN_UP;
import static com.team1.qrnavigationproject.configuration.QRNavigationPaths.LOGIN;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class FullAuthenticationContainerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private RoleRepo roleRepo;

    @Autowired
    private UserTypeRepo userTypeRepo;

    @Order(1)
    @WithMockUser(username="Benjamin", password = "B@55w07d", roles = "ADMIN")
    @DisplayName("Verify that admin can login")
    @Test
    void adminCanLogin() throws Exception{
        mockMvc.perform(get(LOGIN)
                .with(csrf())
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("username", "Benjamin")
                .param("password", "B@55w07d"))
                .andExpect(status().isOk());
    }

    @Order(2)
    @DisplayName("Verify that an anonymous user can sign up an admin account")
    @Test
    void anonymousUserCanSignUp() throws Exception{
        User david = TestData.createDavid();
        david.add(new Role("ADMIN"));
        david.add(new UserType("Adult"));
        mockMvc.perform(post((AUTH_SIGN_UP))
                .with(csrf())
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("username",david.getUsername())
                .param("password", david.getPassword()))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/login"));

    }

}
