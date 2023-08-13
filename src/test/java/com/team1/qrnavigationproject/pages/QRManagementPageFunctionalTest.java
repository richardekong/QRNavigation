package com.team1.qrnavigationproject.pages;


import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
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
public class QRManagementPageFunctionalTest {

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
        final String pageURL = format(BASE_URL + "/admin/qrcodes", port);
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
    public void verifyPresenceOfQRCodeManagementPageElements() {
        //create page elements
        WebElement

                qrSampleImgTag = driver.findElement(By.xpath("//img[@src='/images/qr_code_icon.svg']")),
                h4 = driver.findElement(By.tagName("h4")),
                createQRButton = driver.findElement(By.id("create-btn")),
                tableElement = driver.findElement(By.tagName("table")),
                viewButton = driver.findElements(By.xpath("//a[@href='/admin/qrcodes/update']")).get(0),
                deleteButton = driver.findElements(By.xpath("//button[contains(text(), 'Delete')]")).get(0);

        //verify if these elements are not null
        assertNotNull(qrSampleImgTag);
        assertNotNull(h4);
        assertNotNull(createQRButton);
        assertNotNull(tableElement);
        assertNotNull(viewButton);
        assertNotNull(deleteButton);

        //verify if these elements are displayed on the screen
        assertTrue(qrSampleImgTag.isDisplayed());
        assertTrue(h4.isDisplayed());
        assertTrue(createQRButton.isDisplayed());
        assertTrue(tableElement.isDisplayed());
        assertTrue(viewButton.isDisplayed());
        assertTrue(deleteButton.isDisplayed());

        //verify the text associated with this elements
        assertEquals(h4.getText(), "Manage your QR codes");
        assertEquals(viewButton.getText(), "View");
        assertEquals(deleteButton.getText(), "Delete");

    }

    @Test
    public void verifyCreateQRButtonClick() {
        //find element with link: '/admin/qrcodes/generate', and click on it
        WebElement createQRBtn = driver.findElement(
                By.xpath("//button[@id='create-btn']")
        );

        JavascriptExecutor executor = (JavascriptExecutor) driver;
        executor.executeScript("arguments[0].click();", createQRBtn);

        //After clicking, verify if the current link is now: '/admin/qrcodes/generate'
        assertEquals(driver.getCurrentUrl(), format(BASE_URL + "/admin/qrcodes/generate", port));


        //verify presence and display of specific elements within the qrcodeGeneration.html page
        WebElement
                mainSpaceDropDown = driver.findElement(By.name("main-space")),
                subSpaceDropDown = driver.findElement(By.name("sub-space")),
                descriptionTextArea = driver.findElement(By.name("description")),
                generateBtn = driver.findElement(By.xpath("//button[contains(text(), 'Generate QR code')]")),
                downloadBtn = driver.findElement(By.xpath("//button[contains(text(), 'Download')]"));

        assertTrue(mainSpaceDropDown.isDisplayed());
        assertTrue(subSpaceDropDown.isDisplayed());
        assertTrue(descriptionTextArea.isDisplayed());
        assertTrue(generateBtn.isDisplayed());
        assertTrue(downloadBtn.isDisplayed());
    }

    @Test
    public void verifyCreateViewButtonClick() {
        //find an element with link: '/admin/qrcodes/update
        WebElement viewBtn = driver.findElements(By.xpath("//a[@href='/admin/qrcodes/update']")).get(0);

        //click on the view button with JavaScriptExecutor
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        executor.executeScript("arguments[0].click();", viewBtn);

        //After clicking, verify if the current link is now: '/admin/qrcodes/update
        assertEquals(driver.getCurrentUrl(), format(BASE_URL + "/admin/qrcodes/update", port));

        //verify presence and display of specific elements within the qrcodeUpdate.html page
        WebElement
                qrcodeDropDown = driver.findElement(By.name("qrCode")),
                mainSpaceDropDown = driver.findElement(By.name("main-space")),
                subSpaceDropDown = driver.findElement(By.name("sub-space")),
                descriptionTextArea = driver.findElement(By.name("description")),
                editBtn = driver.findElement(By.xpath("//button[contains(text(), 'Edit')]")),
                downloadBtn = driver.findElement(By.xpath("//button[contains(text(), 'Download')]"));

        assertTrue(qrcodeDropDown.isDisplayed());
        assertTrue(mainSpaceDropDown.isDisplayed());
        assertTrue(subSpaceDropDown.isDisplayed());
        assertTrue(descriptionTextArea.isDisplayed());
        assertTrue(editBtn.isDisplayed());
        assertTrue(downloadBtn.isDisplayed());

    }

    @Test
    public void verifyAdminNavigationItemClicks() {

        driver.findElement(By.xpath("//a[@href='/admin/qrcodes']")).click();
        assertEquals(driver.getCurrentUrl(), format(BASE_URL + "/admin/qrcodes", port));

        driver.findElement(By.xpath("//a[@href='/admin/contents']")).click();
        assertEquals(driver.getCurrentUrl(), format(BASE_URL + "/admin/contents", port));
        //Todo: Fasial will resolve a bug associated with navigating to the event section, until then this portion of the test remains commented
//        driver.findElement(By.xpath("//a[@href='/admin/events']")).click();
//        assertEquals(driver.getCurrentUrl(), format(BASE_URL + "/admin/events", port));

        driver.findElement(By.xpath("//a[@href='/admin/places']")).click();
        assertEquals(driver.getCurrentUrl(), format(BASE_URL + "/admin/places", port));

    }

}
