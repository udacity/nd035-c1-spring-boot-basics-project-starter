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

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FileTests {

    @LocalServerPort
    private Integer port;

    private static WebDriver driver;
    private SignupPage signupPage;
    private LoginPage loginPage;
    private FileObject fileObject;
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
    void fileUpload() {

        // first step: signup and login
        userSignupAndLogin();

        driver.get(baseUrl + "/home");

        fileObject = new FileObject(driver);

        fileObject.upload("/Users/sami/Desktop/Screen Shot 2020-08-20 at 12.24.07 PM.png");

        String actualUrl = driver.getCurrentUrl();

        resultPage = new ResultPage(driver);

        String successMsg = resultPage.getSuccessMsg();

        assertEquals("File upload successful!", successMsg);


    }

    @Test
    void fileDelete() {
        // first step: signup and login
        userSignupAndLogin();

        driver.get(baseUrl + "/home");

        fileObject = new FileObject(driver);

        fileObject.upload("/Users/sami/Desktop/Screen Shot 2020-08-20 at 12.24.07 PM.png");

        driver.get(baseUrl + "/home");

        fileObject = new FileObject(driver);

        fileObject.delete();

        String actualUrl = driver.getCurrentUrl();

        resultPage = new ResultPage(driver);

        String successMsg = resultPage.getSuccessMsg();

        assertEquals("File successfully deleted", successMsg);

    }

    @Test
    void duplicateFilenameIsNotAllowedForSingleUsername() {

        // first step: signup and login
        userSignupAndLogin();

        driver.get(baseUrl + "/home");

        fileObject = new FileObject(driver);

        fileObject.upload("/Users/sami/Desktop/Screen Shot 2020-08-20 at 12.24.07 PM.png");

        driver.get(baseUrl + "/home");

        fileObject = new FileObject(driver);

        String filename = "Screen Shot 2020-08-20 at 12.24.07 PM.png";

        fileObject.upload("/Users/sami/Desktop/" + filename);

        String actualUrl = driver.getCurrentUrl();

        resultPage = new ResultPage(driver);

        String errorMsg = resultPage.getErrorMsg();

        assertEquals("File already exists: " + filename, errorMsg);



    }
}
