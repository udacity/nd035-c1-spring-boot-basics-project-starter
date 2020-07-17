package com.udacity.jwdnd.course1.cloudstorage;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
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
        Thread.sleep(5000);
        getHomePage();
        WebElement navNotesTab = homePage.getNavNotesTab();
        if (navNotesTab == null) {
            System.out.println("Why is this null???");
            return;
        }
        Thread.sleep(5000);
        homePage.getShowNoteModelButton().click();
        Thread.sleep(5000);
    }
}
