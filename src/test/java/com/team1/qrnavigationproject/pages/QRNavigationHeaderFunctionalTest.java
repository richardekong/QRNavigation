package com.team1.qrnavigationproject.pages;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static java.lang.String.format;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class QRNavigationHeaderFunctionalTest {

    @Value("${local.server.port}")
    private int port;
    WebDriver driver;
    private static final String APP_NAME = "QR NAVIGATION SYSTEM";

    @BeforeAll
    static void setupClass() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    void setupTest() {
        String pageURL = format("http://localhost:%d/qrnavigation/header", port);
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--headless");
        driver = new ChromeDriver(options);
        //access the page
        driver.get(pageURL);
    }

    @AfterEach
    void teardown() {
        driver.quit();
    }

    @Test
    public void verifyPresenceOfAppNameText() {
        final WebElement spanElement = driver.findElement(By.id("app-name"));
        assertNotNull(spanElement.getText());
        assertEquals(spanElement.getText(), APP_NAME);
        assertTrue(spanElement.isDisplayed());
    }

    @Test
    public void verifyPresenceOfHeader() {
        final WebElement headerElement = driver.findElement(By.tagName("header"));
        assertNotNull(headerElement);
        assertTrue(headerElement.isDisplayed());
    }

    @Test
    public void verifyPresenceOfLogo() {
        final WebElement imgElement = driver.findElement(By.id("app-logo"));
        assertNotNull(imgElement);
        assertFalse(imgElement.getAttribute("src").isEmpty());
        assertTrue(imgElement.isDisplayed());
    }

    @Test
    public void verifyPresenceOfCallToAction() {
        final WebElement formElement = driver.findElement(By.id("header-auth-form"));
        final WebElement childSpanElement = formElement.findElement(By.tagName("span"));
        final WebElement childImgElement = formElement.findElement(By.tagName("img"));
        final String logoutURL = format("http://localhost:%d/logout",port);
        final String link = formElement.getAttribute("action");
        assertNotNull(formElement);
        assertNotNull(link);
        assertFalse(link.isEmpty());
        assertTrue(link.equals(logoutURL));

        assertNotNull(childSpanElement);
        assertFalse(childSpanElement.getText().isEmpty());
        assertTrue(childSpanElement.getText().equals("Login") ||
                childSpanElement.getText().equals("Logout"));
        assertTrue(childSpanElement.isDisplayed());

        assertNotNull(childImgElement);
        assertNotNull(childImgElement.getAttribute("src"));
        assertFalse(childImgElement.getAttribute("src").isEmpty());
        assertTrue(childImgElement.isDisplayed());

    }

}
