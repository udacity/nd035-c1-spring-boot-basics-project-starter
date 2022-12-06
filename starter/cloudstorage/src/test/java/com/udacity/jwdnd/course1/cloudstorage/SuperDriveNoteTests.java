package com.udacity.jwdnd.course1.cloudstorage;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SuperDriveNoteTests {

    @LocalServerPort
    public int port;
    public static WebDriver driver;
    public String baseUrl;
    private SignUpPage signUpPage;
    private LoginPage loginPage;
    private HomePage homePage;

    @BeforeAll
    public static void beforeAll() {
        WebDriverManager.firefoxdriver().setup();
        driver = new FirefoxDriver();
    }

    @BeforeEach
    public void beforeEach() {
        baseUrl = "http://localhost:" + port;
        signUpPage = new SignUpPage(driver);
        loginPage = new LoginPage(driver);
        homePage = new HomePage(driver);
    }

    @AfterAll
    public static void afterAll() {
        driver.quit();
        driver = null;
    }

    @Test
    public void whenNoteCreated_thenNoteDisplayed() throws InterruptedException {
       createNewNote();

        // Check note added
        assertEquals("note1", homePage.getFirstNoteTitle());
        assertEquals("test1", homePage.getFirstNoteDescription());

        homePage.deleteNote();
        homePage.logout();
    }

    @Test
    public void givenExistingNote_whenEditing_thenNoteIsUpdated() throws InterruptedException {
        createNewNote();
        homePage.editNote("new title", "new description");
        Thread.sleep(2000);

        homePage.goToNotesTab();
        assertEquals("new title", homePage.getFirstNoteTitle());
        assertEquals("new description", homePage.getFirstNoteDescription());

        homePage.deleteNote();
        homePage.logout();
    }

    @Test
    public void givenNote_whenDeleteClicked_thenNoteDeleted() throws InterruptedException {
        createNewNote();

        homePage.deleteNote();
        Thread.sleep(1000);

        assertFalse(homePage.isNotePresent());

        homePage.logout();
    }
    private void createNewNote() throws InterruptedException {
        driver.get(baseUrl + "/signup");
        signupAndLogin("PocketWatch", "12345");

        // Go to home page
        driver.get(baseUrl + "/home");
        WebDriverWait webDriverWait = new WebDriverWait(driver, 3);
        WebElement homeMarker = webDriverWait.until(webDriver -> webDriver.findElement(By.id("nav-files-tab")));
        assertNotNull(homeMarker);

        // Create note
        homePage.createNote("note1", "test1");
        Thread.sleep(3000);
        homePage.goToNotesTab();
    }

    private void signupAndLogin(String userName, String password) {
        signUpPage.signupNewUser("Pocket", "Dal", userName, password);

        driver.get(baseUrl + "/login");
        loginPage.loginUser(userName, password);
    }
}
