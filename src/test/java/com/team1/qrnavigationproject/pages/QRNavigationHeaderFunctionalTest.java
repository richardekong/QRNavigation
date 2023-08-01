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

import static org.junit.jupiter.api.Assertions.*;
import static java.lang.String.format;

@ExtendWith(MockitoExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class QRNavigationHeaderFunctionalTest {

    @Value("${local.server.port}")
    private int port;
    WebDriver driver;
    private String pageURL;
    private static final String APP_NAME = "QR NAVIGATION SYSTEM";

    @BeforeAll
    static void setupClass() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    void setupTest() {
        pageURL = format("http://localhost:%d/qrnavigation", port);
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
        final WebElement anchorElement = driver.findElements(By.className("auth-link")).get(0);
        final WebElement childSpanElement = anchorElement.findElement(By.tagName("span"));
        final WebElement childImgElement = anchorElement.findElement(By.tagName("img"));
        final String loginURL = pageURL+"/login";
        final String logoutURL = pageURL+"/logout";
        final String link = anchorElement.getAttribute("href");
        assertNotNull(anchorElement);
        assertNotNull(link);
        assertFalse(link.isEmpty());
        assertTrue(link.equals(loginURL) || link.equals(logoutURL));

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

    @Test
    public void verifyLoginSlashLoginTextToggle() {
        //TODO latter
        System.out.println("TODO latter");
    }
}
