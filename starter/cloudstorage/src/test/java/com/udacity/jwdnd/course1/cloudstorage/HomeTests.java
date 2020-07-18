package com.udacity.jwdnd.course1.cloudstorage;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class HomeTests {
    @LocalServerPort
    private Integer port;

    private static WebDriver driver;
    private HomePageObject homePage;
    private LoginPageObject loginPage;
    private SignupPageObject signupPage;
    private ResultPageObject resultPage;
    private final String BASE_URL = "http://localhost:";



    @BeforeAll
    public static void beforeAll() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
    }

    @AfterAll
    public static void afterAll() {
        driver.quit();
    }

    @BeforeEach
    public void beforeEach() {
        driver.get("http://localhost:" + port + "/login");
        loginPage = new LoginPageObject(driver);
    }

    private void signUpAndLogin() throws InterruptedException {
        System.out.println(">>> signUpAndLogin");
        String username = "michael777";
        String password = "whatabadpassword";
        signupPage = new SignupPageObject(driver);
        driver.get(BASE_URL + port + "/signup");

        SignupPageObject signupPage = new SignupPageObject(driver);
        signupPage.signup("Michael", "Schumacher", username, password);
        signupPage.getSubmitButton().click();

        driver.get(BASE_URL + port + "/login");
        LoginPageObject loginPage = new LoginPageObject(driver);
        loginPage.login(username, password);
        loginPage.getSubmitButton().click();
    }

    private void getHomePage() {
        driver.get(BASE_URL + port + "/home");
        homePage = new HomePageObject(driver);
    }

    @Test
    public void homePageNotAccessibleWhenNotLoggedIn() {
        getHomePage();
        String currentURL = driver.getCurrentUrl();
        System.out.println("indexOf: " + currentURL.indexOf("home"));
        System.out.println(currentURL);
        assertTrue(currentURL.indexOf("home") == -1);
    }

    @Test
    public void signUpLogInVerifyAccessHomeLogOutNoAccessHomePage() throws InterruptedException {
        signUpAndLogin();
        String currentURL = driver.getCurrentUrl();
        assertTrue(currentURL.indexOf("home") >= 0);
        getHomePage();
        homePage.getLogOutButton().click();
        currentURL = driver.getCurrentUrl();
        // Make sure we did logout and on the login page
        assertTrue(currentURL.indexOf("login") >= 0);
        driver.get(BASE_URL + port + "/home");
        currentURL = driver.getCurrentUrl();
        assertTrue(currentURL.indexOf("login") >= 0);
    }

    @Test
    public void createNoteAndSeeInList() throws InterruptedException {
        signUpAndLogin();
        WebDriverWait wait = new WebDriverWait (driver, 500);
        getHomePage();
        WebElement navNotesTab = homePage.getNavNotesTab();

        // wait till the webelement loads before performing an action on it.
        wait.until(ExpectedConditions.elementToBeClickable(navNotesTab)).click();

        WebElement addNoteModalButton = homePage.getShowNoteModelButton();
        wait.until(ExpectedConditions.elementToBeClickable(addNoteModalButton)).click();

        WebElement notesTitle = homePage.getNoteTitle();
        wait.until(ExpectedConditions.elementToBeClickable(notesTitle));
        notesTitle.sendKeys("This is the title of the note");

        WebElement notesDescription = homePage.getNoteDescription();
        wait.until(ExpectedConditions.elementToBeClickable(notesDescription));
        notesDescription.sendKeys(("This is the description, if real, it would be interesting"));

        WebElement noteSubmit = homePage.getSubmitNoteButtonFooter();
        wait.until(ExpectedConditions.elementToBeClickable(noteSubmit));
        noteSubmit.click();

        // Click on Success/Continue on the result page

        System.out.println("PageSource: " + driver.getPageSource());
        Thread.sleep(10000);
        driver.get(BASE_URL + port + "/result");
        resultPage = new ResultPageObject(driver);

        WebElement saveContinue = resultPage.getSaveContinue();
        wait.until(ExpectedConditions.elementToBeClickable(saveContinue));
        saveContinue.click();

        // Click on the Logout button
        // Try to navigate to home page
        // Assert that the URL is login.

        Thread.sleep(10000);
    }
}