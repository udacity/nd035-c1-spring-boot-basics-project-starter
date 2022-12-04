package com.udacity.jwdnd.course1.cloudstorage;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SuperDriveAuthenticationTests {

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
    public void givenUnauthorizedUser_whenAccessToHome_thenLoginPageIsDisplayed() {
        // tests that verifies that an unauthorized user can only access the login page.

        driver.get(baseUrl + "/home");
        assertEquals("Login", driver.getTitle());
    }

    @Test
    void givenUnauthorizedUser_whenAccessToSignup_thenSignupPageIsDisplayed() {
        // tests that verifies that an unauthorized user can only access the signup pages.

        driver.get(baseUrl + "/signup");
        assertEquals("Sign Up", driver.getTitle());
    }

    @Test
    public void givenNewUser_whenSignupAndLogin_thenHomePageAccessible() {
        // signs up a new user, logs in, verifies that the home page is accessible, logs out,
        // and verifies that the home page is no longer accessible.

        String testUsername = "sergiot";
        String testPassword = "12345";

        // signup new user
        driver.get(baseUrl + "/signup");
        signUpPage.signupNewUser("Sergio", "T", testUsername, testPassword);

        signUpPage.goToLoginPage(driver);
        //driver.get(baseUrl + "/login");

        // log in user
        loginPage.loginUser(testUsername, testPassword);

        // verify home page is accessible
        assertEquals("Home", driver.getTitle());

        // logout
        homePage.logout();
        assertEquals("Login", driver.getTitle());
    }
}
