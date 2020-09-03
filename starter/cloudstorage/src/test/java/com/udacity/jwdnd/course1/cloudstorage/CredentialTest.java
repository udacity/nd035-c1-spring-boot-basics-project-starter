package com.udacity.jwdnd.course1.cloudstorage;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CredentialTest {

    @LocalServerPort
    private Integer port;

    private static WebDriver driver;
    private SignupPage signupPage;
    private LoginPage loginPage;
    private HomePage homePage;
    private ResultPage resultPage;
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

    public void userSignupAndLogin() {
        String firstName = "Samson";
        String lastName = "Hailu";
        String userName = "samsino";
        String password = "mypassword";

        // signup
        driver.get(baseUrl + "/signup");
        signupPage = new SignupPage(driver);
        signupPage.registerUser(firstName, lastName, userName, password);


        // login
        driver.get(baseUrl + "/login");
        loginPage = new LoginPage(driver);
        loginPage.login(userName, password);
    }

    @Test
    void addCredential() {
        userSignupAndLogin();

        driver.get(baseUrl + "/home");

        homePage = new HomePage(driver);

        homePage.addCredential(driver, "http://maincoders.com", "samsino", "pa$$w0rd");

        driver.get(baseUrl + "/home");

//        homePage = new HomePage(driver);

        final Map<String, String> credential = homePage.getCredential(driver);

        assertAll(
                () -> assertEquals("http://maincoders.com", credential.get("url")),
                () -> assertEquals("samsino", credential.get("username")),
                () -> assertNotEquals("pa$$w0rd", credential.get("password"))
        );

        driver.get(baseUrl + "/home");

        homePage = new HomePage(driver);

        homePage.editCredential(driver, "http://maincoders.com/login", "sam","pa$$w0rd");

        driver.get(baseUrl + "/home");

        final Map<String, String> updatedCredential = homePage.getCredential(driver);

        assertAll(
                () -> assertEquals("http://maincoders.com/login", credential.get("url")),
                () -> assertEquals("sam", credential.get("username")),
                () -> assertNotEquals("pa$$w0rd", credential.get("password"))
        );



    }

}
