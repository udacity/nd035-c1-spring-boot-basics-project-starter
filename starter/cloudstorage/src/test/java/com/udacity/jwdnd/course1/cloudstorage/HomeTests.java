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

    // TODO  figure out how to verify that the home page isn't accessable...
    @Test
    public void homePageNotAccesableNotLoggedIn() throws InterruptedException {
//        signUpAndLogin();
        driver.get(BASE_URL + port + "/home");
        homePage = new HomePageObject(driver);
        Thread.sleep(5000);
    }
}
