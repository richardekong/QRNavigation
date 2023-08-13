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
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static java.lang.String.format;

@ExtendWith(MockitoExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class LoginFunctionalTest {

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
        String pageURL = BASE_URL.formatted(port) + "/login";
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--headless");
        driver = new ChromeDriver(options);
        driver.get(pageURL);
    }

    @AfterEach
    void teardown() {
        driver.quit();
    }

    @Test
    public void verifyLoginRequest(){
        //        //Request and access the login page
        driver.get(format(BASE_URL + "/login", port));

        //click the "Don't have an account?" link to request and access the signup page
        driver.findElement(By.xpath("//a[@href='/qrnavigation/signup']")).click();

        //key in email and password
        driver.findElement(By.id("email")).sendKeys("admin@cardiff.ac.uk");
        driver.findElement(By.id("password")).sendKeys("admin123");
        //click the signup button to go back to the login page
        driver.findElement(By.xpath("//button[@type='submit']")).click();

        assert(driver.getCurrentUrl().equals(format(BASE_URL + "/login", port)));

    }

}
