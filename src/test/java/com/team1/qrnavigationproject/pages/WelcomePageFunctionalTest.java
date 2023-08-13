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
public class WelcomePageFunctionalTest {

    @Value("${local.server.port}")
    private int port;

    WebDriver driver;

    @BeforeAll
    static void setupClass() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    void setupTest() {
        String pageURL = format("http://localhost:%d/qrnavigation/welcome", port);
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
    public void verifyPresenceOfHeader(){
        WebElement  header = driver.findElement(By.tagName("header"));
        assertNotNull(header);
        assertTrue(header.isDisplayed());
    }

    @Test
    public void verifyPresenceOfFooter(){
        WebElement  footer = driver.findElement(By.tagName("footer"));
        assertNotNull(footer);
        assertTrue(footer.isDisplayed());
    }


    @Test
    public void verifyLoginClick(){
        driver.findElement(By.xpath("//button[@type='submit']")).click();
        assertEquals(driver.getCurrentUrl(), format("http://localhost:%d/login",port));
    }

}
