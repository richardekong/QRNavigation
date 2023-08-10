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

import java.util.regex.Pattern;

import static com.team1.qrnavigationproject.model.Constant.*;
import static java.lang.String.format;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertFalse;

@ExtendWith(MockitoExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class OrganizationRegistrationPageFunctionalTest {

    @Value("${local.server.port}")
    private int port;

    WebDriver driver;

    @BeforeAll
    static void setupClass() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    void setupTest() {
        final String pageURL = format("http://localhost:%d/admin/organization/register", port);
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
    public void verifyPageSectionHeader() {
        String h3Text;
        h3Text = driver.findElement(By.tagName("H3")).getText();
        assertEquals(h3Text, "Register your Organization's Details");
    }

    @Test
    public void verifyPresenceOfFormInputTags() {
        assertNotNull(driver.findElement(By.name("name")));
        assertNotNull(driver.findElement(By.name("phone")));
        assertNotNull(driver.findElement(By.name("postcode")));
        assertNotNull(driver.findElement(By.name("description")));
        assertNotNull(driver.findElement(By.name("logoURL")));
        assertNotNull(driver.findElement(By.name("websiteURL")));
        assertNotNull(driver.findElement(By.name("headerBackground")));
        assertNotNull(driver.findElement(By.name("footerBackground")));
        assertEquals(driver.findElement(By.tagName("button")).getText(),"Register");
    }

    @Test
    public void verifyFormInputValidation() {
        WebElement
                nameInput = driver.findElement(By.name("name")),
                phoneInput = driver.findElement(By.name("phone")),
                postcodeInput = driver.findElement(By.name("postcode")),
                logoInput = driver.findElement(By.name("logoURL")),
                websiteInput = driver.findElement(By.name("websiteURL"));

        Pattern
                namePattern = Pattern.compile(NAME_REGEX),
                phonePattern = Pattern.compile(PHONE_REGEX),
                postcodePattern = Pattern.compile(POSTCODE_REGEX),
                logoPattern = Pattern.compile(IMAGE_URL_REGEX),
                websitePattern = Pattern.compile(WEBSITE_URL_REGEX);

        //verify name validation functionality
        nameInput.sendKeys("Cardiff University");
        assertTrue(namePattern.matcher(nameInput.getAttribute("value")).matches());
        nameInput.sendKeys("R@67345434");
        assertFalse(namePattern.matcher(nameInput.getAttribute("value")).matches());

        //verify phone validation functionality
        phoneInput.sendKeys("+445634313450");
        assertTrue(phonePattern.matcher(phoneInput.getAttribute("value")).matches());
        phoneInput.sendKeys("Fake Telephone");
        assertFalse(phonePattern.matcher(phoneInput.getAttribute("value")).matches());

        //verify postcode validation functionality
        postcodeInput.sendKeys("CF24 4AG");
        assertTrue(postcodePattern.matcher(postcodeInput.getAttribute("value")).matches());
        postcodeInput.sendKeys("Fake KK 2XX");
        assertFalse(postcodePattern.matcher(postcodeInput.getAttribute("value")).matches());

        //verify logo url validation functionality
        logoInput.sendKeys("https://www.cardiffuni.org/logo.png");
        assertTrue(logoPattern.matcher(logoInput.getAttribute("value")).matches());
        logoInput.sendKeys("etc.cardiffuni.org/logo");
        assertFalse(logoPattern.matcher(logoInput.getAttribute("value")).matches());

        //verify website url  validation functionality
        websiteInput.sendKeys("https://www.cardiffuni.org");
        assertTrue(websitePattern.matcher(websiteInput.getAttribute("value")).matches());
        websiteInput.sendKeys("**/cardiffuni.org/logo");
        assertFalse(websitePattern.matcher(websiteInput.getAttribute("value")).matches());
    }

}
