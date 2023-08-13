package com.team1.qrnavigationproject.fullContainerTest;


import com.team1.qrnavigationproject.configuration.QRNavigationPaths;
import com.team1.qrnavigationproject.model.*;
import com.team1.qrnavigationproject.repository.AddressRepo;
import com.team1.qrnavigationproject.repository.LocationRepo;
import com.team1.qrnavigationproject.repository.OrganizationRepo;
import com.team1.qrnavigationproject.repository.UserRepo;
import com.team1.qrnavigationproject.stub.TestData;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static com.team1.qrnavigationproject.configuration.QRNavigationPaths.*;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class FullOrganizationContainerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    OrganizationRepo orgRepo;

    @Autowired
    UserRepo userRepo;

    @Autowired
    LocationRepo locRepo;

    @Autowired
    AddressRepo addressRepo;

    @PersistenceContext
    private EntityManager entityManager;

    @Order(1)
    @WithMockUser(username = "Benjamin", password = "B@55w07d", roles = "ADMIN")
    @DisplayName("Verify that Admin can access organization registration page")
    @Test
    void adminCanAccessOrganizationRegistrationPage() throws Exception {

        mockMvc.perform(get(ADMIN_ORG_REG_PAGE)
                        .with(csrf())
                        .contentType(MediaType.TEXT_HTML))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Register your Organization's Details")));
    }

    @Order(2)
    @WithMockUser(username = "Benjamin", password = "B@55w07d", roles = "ADMIN")
    @DisplayName("Verify that an admin can register his organization")
    @Test
    void adminCanRegisterOrganization() throws Exception {

        User currentUser = userRepo.findUserByUsername(
                        SecurityContextHolder.getContext()
                                .getAuthentication().getName())
                .orElseThrow();

        Organization organization = TestData.createOrganization();
        organization.setUser(currentUser);
        Location loc = TestData.createLocation();
        Address address = TestData.createAddress();
        loc.setAddress(address);
        address.setLocation(loc);
        address.setOrganization(organization);
        organization.setAddress(address);

        mockMvc.perform(post((ADMIN_ORG_REG_PROCESS))
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("name", organization.getName())
                        .param("phone", organization.getPhone())
                        .param("postcode", address.getPostcode())
                        .param("description", address.getDescription())
                        .param("logoURL", organization.getLogoURL())
                        .param("website", organization.getWebsiteURL())
                        .param("headerBackground", organization.getHeaderBackground())
                        .param("footerBackground", organization.getFooterBackground()))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/admin/main"));

    }

    @Order(3)
    @WithMockUser(username = "Benjamin", password = "B@55w07d", roles = "ADMIN")
    @DisplayName("Verify that an admin can update his organization")
    @Test
    void adminCanUpdateOrganization() throws Exception {

        User currentUser = userRepo.findUserByUsername(
                        SecurityContextHolder.getContext()
                                .getAuthentication().getName()
                )
                .orElseThrow();
        Organization organization = TestData.createOrganization();
        organization.setUser(currentUser);
        Location loc = TestData.createLocation();
        Address address = TestData.createAddress();
        loc.setAddress(address);
        address.setLocation(loc);
        address.setOrganization(organization);
        organization.setAddress(address);

        mockMvc.perform(post(ADMIN_ORG_UPDATE_PROCESS)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("phone", "0736434653434")
                        .param("postcode", "CF24 3AA"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/admin/main"));

    }


    @Order(4)
    @WithMockUser(username = "Benjamin", password = "B@55w07d", roles = "ADMIN")
    @DisplayName("Verify that Admin can access organization update page")
    @Test
    void adminCanAccessOrganizationUpdatePage() throws Exception {

        mockMvc.perform(get(QRNavigationPaths.ADMIN_ORG_UPDATE_PAGE)
                        .with(csrf())
                        .contentType(MediaType.TEXT_HTML))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Edit your Organization's Details")));
    }


}
