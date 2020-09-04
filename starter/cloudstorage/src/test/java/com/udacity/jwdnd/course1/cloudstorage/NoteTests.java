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

import java.util.Map;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class NoteTests {

    @LocalServerPort
    private Integer port;

    private static WebDriver driver;
    private SignupPage signupPage;
    private LoginPage loginPage;
    private NotePage notePage;
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
    void addNote() {
        userSignupAndLogin();

        driver.get(baseUrl + "/home");

        notePage = new NotePage(driver);

        notePage.addNote(driver, "Todo", "finalize ecommerce website");

        String actualUrl = driver.getCurrentUrl();

        resultPage = new ResultPage(driver);

        String successMsg = resultPage.getSuccessMsg();

        assertEquals("Note successfully added!", successMsg);

        driver.get(baseUrl + "/home");

        final Map<String, String> notes = notePage.getNote(driver);

        assertAll(
                () -> assertEquals("Todo", notes.get("title")),
                () -> assertEquals("finalize ecommerce website", notes.get("desc"))
        );



    }

    @Test
    void editNote() {

        userSignupAndLogin();

        driver.get(baseUrl + "/home");

        notePage = new NotePage(driver);

        notePage.addNote(driver, "Todo", "finalize ecommerce website");

        driver.get(baseUrl + "/home");

        notePage.editNote(driver, "Todo 1", "Add login feature");

        resultPage = new ResultPage(driver);

        String successMsg = resultPage.getSuccessMsg();

        assertEquals("Note successfully edited!", successMsg);

        driver.get(baseUrl + "/home");

        final Map<String, String> notes = notePage.getNote(driver);

        assertAll(
                () -> assertEquals("Todo 1", notes.get("title")),
                () -> assertEquals("Add login feature", notes.get("desc"))
        );

    }

    @Test
    void deleteNote() {
        userSignupAndLogin();

        driver.get(baseUrl + "/home");

        notePage = new NotePage(driver);

        notePage.addNote(driver, "Todo", "finalize ecommerce website");

        driver.get(baseUrl + "/home");

        notePage.deleteNote(driver);

        String actualUrl = driver.getCurrentUrl();

        resultPage = new ResultPage(driver);

        String successMsg = resultPage.getSuccessMsg();

        assertEquals("Note successfully deleted!", successMsg);
    }
}
