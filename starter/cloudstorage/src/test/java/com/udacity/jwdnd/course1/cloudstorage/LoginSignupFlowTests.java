package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class LoginSignupFlowTests {

    @LocalServerPort
    private int port;

    private WebDriver driver;

    public final String USERNAME = "testuser";
    private final String PASSWORD = "testpassword";

    @Autowired
    private UserService userService;

    @BeforeAll
    static void beforeAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    public void beforeEach() {
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.setPageLoadStrategy(PageLoadStrategy.EAGER);
        chromeOptions.setHeadless(true);
        this.driver = new ChromeDriver(chromeOptions);
    }

    @AfterEach
    public void afterEach() {
        if (this.driver != null) {
            driver.quit();
        }
        this.userService.deleteUser(USERNAME);
    }

    @Test
    public void getLoginPage() {
        driver.get("http://localhost:" + this.port + "/login");
        assertEquals("Login", driver.getTitle());
    }

    @Test
    public void getHomePage() {
        driver.get("http://localhost:" + this.port + "/");
        assertEquals("Login", driver.getTitle());
    }

    @Test
    public void NoSignupLoginFail() {
        WebDriverWait wait = new WebDriverWait(driver, 2);

        TestHelper.login(driver, this.port, USERNAME, PASSWORD);

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("errorDiv")));
        assertTrue(driver.findElement(By.id("errorDiv")).isDisplayed());
        assertEquals("Login", driver.getTitle());
    }

    @Test
    public void signupAndLoginOk() {
        WebDriverWait wait = new WebDriverWait(driver, 2);

        //signup
        this.signup(USERNAME, PASSWORD);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("signupSuccessDiv")));
        assertTrue(driver.findElement(By.id("signupSuccessDiv")).isDisplayed());

        //login
        TestHelper.login(driver, this.port, USERNAME, PASSWORD);
        assertEquals("Home", driver.getTitle());

        //assert that user exists on db
        assertNotEquals(-1, this.userService.getUserId(USERNAME));
    }

    @Test
    public void signupAndLoginFails() {
        WebDriverWait wait = new WebDriverWait(driver, 2);

        //signup user
        this.signup(USERNAME, PASSWORD);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("signupSuccessDiv")));
        assertTrue(driver.findElement(By.id("signupSuccessDiv")).isDisplayed());

        //login with wrong password
        TestHelper.login(driver, this.port, USERNAME, "abc");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("errorDiv")));
        assertTrue(driver.findElement(By.id("errorDiv")).isDisplayed());
        assertEquals("Login", driver.getTitle());

        //assert that user exists on db
        assertNotEquals(-1, this.userService.getUserId(USERNAME));
    }

    @Test
    public void signupTwiceFails() {
        WebDriverWait wait = new WebDriverWait(driver, 2);

        //signup
        this.signup(USERNAME, PASSWORD);
        wait.until(ExpectedConditions.elementToBeClickable(By.id("loginLink")));

        //signup again
        this.signup(USERNAME, PASSWORD);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("signupErrorDiv")));
        assertTrue(driver.findElement(By.id("signupErrorDiv")).isDisplayed());

        //assert that user exists on db
        assertNotEquals(-1, this.userService.getUserId(USERNAME));
    }

    private void signup(String username, String password) {
        WebDriverWait wait = new WebDriverWait(driver, 2);

        driver.get("http://localhost:" + this.port + "/signup");
        wait.until(ExpectedConditions.titleIs("Sign Up"));

        WebElement signupForm = driver.findElement(By.id("signupForm"));
        signupForm.findElement(By.id("inputFirstName")).sendKeys("Jane");
        signupForm.findElement(By.id("inputLastName")).sendKeys("Doe");
        signupForm.findElement(By.id("inputUsername")).sendKeys(username);
        signupForm.findElement(By.id("inputPassword")).sendKeys(password);

        wait.until(ExpectedConditions.attributeToBe(By.id("inputUsername"), "value", username));
        signupForm.submit();
    }
}
