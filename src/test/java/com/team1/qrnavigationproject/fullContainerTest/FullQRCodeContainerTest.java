package com.team1.qrnavigationproject.fullContainerTest;


import com.team1.qrnavigationproject.repository.QRCodeRepo;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import javax.transaction.Transactional;

import static com.team1.qrnavigationproject.configuration.QRNavigationPaths.*;
import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class FullQRCodeContainerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private QRCodeRepo qrcodeRepo;

    @Order(1)
    @WithMockUser(username = "Benjamin", password = "B@55w07d", roles = "ADMIN")
    @DisplayName("Verify that Admin can access a list of Managed QR code records on the qrcodes page")
    @Test
    void adminCanRequestQRCodeRecords() throws Exception {
        var qrcodes = qrcodeRepo.findAllQRCodes();


        int size = qrcodes.size();
        mockMvc.perform(get(ADMIN_QRCODES)
                        .with(csrf())
                        .contentType(MediaType.TEXT_HTML))
                .andExpect(status().isOk());
        assertEquals(qrcodes.size(), size);
        assertFalse(qrcodes.isEmpty());
    }

    @Order(2)
    @WithMockUser(username = "Benjamin", password = "B@55w07d", roles = "ADMIN")
    @DisplayName("Verify that Admin can request the page for creating a qrcode and associated record")
    @Test
    void adminCanRequestQRGenerationPage() throws Exception {
        mockMvc.perform(get(ADMIN_QRCODES_GENERATE)
                        .with(csrf())
                        .contentType(MediaType.TEXT_HTML))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Generate QR code")))
                .andExpect(content().string(containsString("Download")))
                .andExpect(content().string(containsString("Main space")))
                .andExpect(content().string(containsString("Select a space")))
                .andExpect(content().string(containsString("Subspace")))
                .andExpect(content().string(containsString("Select subspace")));
    }

    @Order(3)
    @WithMockUser(username = "Benjamin", password = "B@55w07d", roles = "ADMIN")
    @DisplayName("Verify that Admin can create a QR code with associated record")
    @Test
    void adminCanCreateQRCode() throws Exception {
        mockMvc.perform(post(ADMIN_QRCODES_GENERATE_PROCESS)
                        .with(csrf())
                        .contentType(MediaType.TEXT_HTML)
                        .param("id", "4")
                        .param("description", "QRCode for Abacws / 3.45")
                        .param("spaceId", "4")
                        .param("subSpaceId", "4")
                        .param("pageURL", "http:www.cardiff.com/QRNAVIGATION/Abacws/3.45")
                        .param("imageURL", "qr1.png"))
                .andExpect(status().is3xxRedirection());

    }

//Todo: Check these commented tests latter

    @Order(4)
    @WithMockUser(username = "Benjamin", password = "B@55w07d", roles = "ADMIN")
    @DisplayName("Verify that Admin can request the page to update mutable attributes of QRCode record")
    @Test
    void adminCanRequestToUpdateQRCodeRecord() throws Exception {

        mockMvc.perform(get(ADMIN_QRCODES_UPDATE + "/{id}", 1)
                        .with(csrf())
                        .contentType(MediaType.TEXT_HTML))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("QR code for room 3.45 @ Abacws building")));
    }

    @Order(5)
    @WithMockUser(username = "Benjamin", password = "B@55w07d", roles = "ADMIN")
    @DisplayName("Verify that Admin can update mutable attributes of records associated with a QRCode")
    @Test
    void adminCanUpdateQRCodeRecord() throws Exception {
        String description = "A bookable room on the fifth floor. This room contains tables, chairs, whiteboards and monitors";


        mockMvc.perform(post(ADMIN_QRCODES_UPDATE_PROCESS)
                        .with(csrf())
                        .contentType(MediaType.TEXT_HTML)
                        .param("id", "1")
                        .param("description", description))
                .andExpect(status().is3xxRedirection())
                .andExpect(flash().attributeExists("success"))
                .andExpect(flash().attribute("success","Success (200): QR code updated successfully"));
    }

    @Order(6)
    @WithMockUser(username = "Benjamin", password = "B@55w07d", roles = "ADMIN")
    @DisplayName("Verify that Admin can delete a QRCode and it's record from the database and storage")
    @Test
    void adminCanDeleteQRCode() throws Exception {

        mockMvc.perform(post(ADMIN_QRCODES)
                        .with(csrf())
                        .contentType(MediaType.TEXT_HTML)
                        .param("id", "1"))
                .andExpect(status().is3xxRedirection());
}


}

