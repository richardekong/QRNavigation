package com.example.qrnavigationproject.pages;


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
public class AdminMainPageFunctionalTest {
    @Value("${local.server.port}")
    private int port;
    WebDriver driver;

    public static final String BASE_URL = "http://localhost:%d";

    @BeforeAll
    static void setupClass() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    void setupTest() {
        final String pageURL = format(BASE_URL + "/admin/main", port);
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
    public void verifyPresenceOfQRCodesLink(){
        WebElement qrAnchorTag = driver.findElement(By.xpath("//a[@href='/admin/qrcodes']"));
        assertNotNull(qrAnchorTag);
        assertTrue(qrAnchorTag.isDisplayed());
        qrAnchorTag.click();
        assertEquals(driver.getCurrentUrl(), format(BASE_URL + "/admin/qrcodes", port));
    }

    @Test
    public void verifyPresenceOfEventsLink(){
        WebElement eventAnchorTag = driver.findElement(By.xpath("//a[@href='/admin/events']"));
        assertNotNull(eventAnchorTag);
        assertTrue(eventAnchorTag.isDisplayed());
        eventAnchorTag.click();
        assertEquals(driver.getCurrentUrl(), format(BASE_URL + "/admin/events", port));
    }

    @Test
    public void verifyPresenceOfContentsLink(){
        WebElement contentAnchorTag = driver.findElement(By.xpath("//a[@href='/admin/contents']"));
        contentAnchorTag.click();
        assertNotNull(contentAnchorTag);
        assertEquals(driver.getCurrentUrl(), format(BASE_URL + "/admin/contents", port));
    }

    @Test
    public void verifyPresenceOfPlaceLink(){
        WebElement placeAnchorTag = driver.findElement(By.xpath("//a[@href='/admin/places']"));
        assertNotNull(placeAnchorTag);
        assertTrue(placeAnchorTag.isDisplayed());
        placeAnchorTag.click();
        assertEquals(driver.getCurrentUrl(), format(BASE_URL + "/admin/places", port));
    }

}

