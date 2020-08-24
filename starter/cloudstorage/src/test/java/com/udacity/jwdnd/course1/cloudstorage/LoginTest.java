package com.udacity.jwdnd.course1.cloudstorage;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class LoginTest {

    @LocalServerPort
    private Integer port;

    private static WebDriver driver;
    private SignupPage signupPage;
    private LoginPage loginPage;
    private HomePage homePage;
    public String baseUrl;

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
        baseUrl = "http://localhost:" + port;
    }

    @Test
    void homePageNotAccessibleWhenNotLoggedIn() {
        driver.get(baseUrl + "/home");
        String expectedUrl = baseUrl + "/login";
        String actualUrl = driver.getCurrentUrl();

        assertEquals(expectedUrl, actualUrl);
    }

    @Test
    void duplicateUsernameIsNotAllowed() {
        String firstName = "Samson";
        String lastName = "Hailu";
        String userName = "samsino";
        String password = "mypassword";

        String duplicateUserName = "samsino";

        // signup
        driver.get(baseUrl + "/signup");
        signupPage = new SignupPage(driver);
        signupPage.registerUser(firstName, lastName, userName, password);

        // signup with duplicate username
        driver.get(baseUrl + "/signup");
        signupPage = new SignupPage(driver);
        signupPage.registerUser(firstName, lastName, duplicateUserName, password);

        assertEquals("The username already exists." , signupPage.getErrorMsg());


    }

    @Test
    void testUserSignupLoginAndLogout() {

        String firstName = "Samson";
        String lastName = "Hailu";
        String userName = "samsino";
        String password = "mypassword";

        // signup
        driver.get(baseUrl + "/signup");
        signupPage = new SignupPage(driver);
        signupPage.registerUser(firstName, lastName, userName, password);

        assertEquals("You successfully signed up! Please continue to the login page.",
                signupPage.getSuccessMsg());

        // login
        driver.get(baseUrl + "/login");
        loginPage = new LoginPage(driver);
        loginPage.login(userName, password);

        // check if login is success i.e. redirected to /home
        assertEquals(baseUrl + "/home", driver.getCurrentUrl());

        // logout
        driver.get(baseUrl + "/home");
        homePage = new HomePage(driver);
        homePage.logout();

        assertEquals(baseUrl + "/login", driver.getCurrentUrl());


    }




}
