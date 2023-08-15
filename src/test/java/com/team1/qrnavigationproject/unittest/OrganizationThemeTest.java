package com.team1.qrnavigationproject.unittest;

import com.team1.qrnavigationproject.model.OrganizationTheme;
import com.team1.qrnavigationproject.stub.TestData;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


class OrganizationThemeTest {

    private OrganizationTheme theme;

    @BeforeEach
    void setUp() {
        theme = new OrganizationTheme(TestData.createOrganization());
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getName() {
        assertNotNull(theme.getName());
       assertEquals(theme.getName(), "Cardiff University");
    }

    @Test
    void getLogoURL() {
        assertNotNull(theme.getLogoURL());
        assertEquals(theme.getLogoURL(),"https://www.cardiffuni.com/logo.png");
    }

    @Test
    void getHeaderBackground() {
        assertNotNull(theme.getHeaderBackground());
        assertEquals(theme.getHeaderBackground(), "#FFFFFF");
    }

    @Test
    void getFooterBackground() {
        assertNotNull(theme.getFooterBackground());
        assertEquals(theme.getFooterBackground(), "#FFFFFF");
    }

    @Test
    void getWebsiteURL() {
        assertNotNull(theme.getWebsiteURL());
        assertEquals(theme.getWebsiteURL(), "https://www.cardiffuni.com");
    }

}

