package com.udacity.jwdnd.course1.cloudstorage;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class OverviewTests {
    @LocalServerPort
    private Integer port;

    private static WebDriver driver;
    private LoginPageObject loginPage;
    private SignupPageObject signupPage;


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

    @Test
    public void testLoginWithInvalidUser() throws InterruptedException {
        loginPage.getInputUserName().sendKeys("frank");
        loginPage.getInputPassword().sendKeys("password");
        loginPage.getSubmitButton().click();
        Thread.sleep(1000);
        String myErrorMsg = loginPage.getErrorMsg().getText();
        assertEquals("Invalid username or password", myErrorMsg);
        Thread.sleep(5000);
    }

    @Test
    public void testLoginCickSignup() throws InterruptedException {
        loginPage.getSignupLinkButton().click();
        driver.get("http://localhost:" + port + "/signup");
        signupPage = new SignupPageObject(driver);
        signupPage.getInputFirstName().sendKeys("frank");
        signupPage.getInputLastName().sendKeys("smith");
        signupPage.getInputUsername().sendKeys("fs");
        signupPage.getInputPassword().sendKeys("fs");
        signupPage.getSubmitButton().click();
        WebElement goBack = signupPage.getLoginLink();
        Assertions.assertNotNull(goBack);
    }

    @Test
    public void testLoginCickSignupAndLogin() throws InterruptedException {
        // TODO - call the other tests instead of constantly calling these...
        // TODO - create final String for the username/password bits...
        loginPage.getSignupLinkButton().click();
        driver.get("http://localhost:" + port + "/signup");
        SignupPageObject signupPage = new SignupPageObject(driver);
        signupPage.getInputFirstName().sendKeys("frank");
        signupPage.getInputLastName().sendKeys("smith");
        signupPage.getInputUsername().sendKeys("fs");
        signupPage.getInputPassword().sendKeys("fs");
        signupPage.getSubmitButton().click();
        WebElement goBack = signupPage.getLoginLink();
        Assertions.assertNotNull(goBack);
        goBack.click();
        loginPage.getInputUserName().sendKeys("fs");
        loginPage.getInputPassword().sendKeys("fs");
        loginPage.getSubmitButton().click();
        Thread.sleep(10000);
    }

    @Test
    public void testUserSignupLoginAndSubmitMessage() {
        String username = "pzastoup";
        String password = "whatabadpassword";
        String baseURL = "http://localhost:" + port;
        driver.get(baseURL + "/signup");

        SignupPageObject signupPage = new SignupPageObject(driver);
        signupPage.signup("Peter", "Zastoupil", username, password);

        driver.get(baseURL + "/login");

        LoginPageObject loginPage = new LoginPageObject(driver);
        loginPage.login(username, password);

        HomePageObject chatPage = new HomePageObject(driver);
//        chatPage.sendChatMessage(messageText);
//        ChatMessage sentMessage = chatPage.getFirstMessage();
//        assertEquals(username, sentMessage.getUsername());
//        assertEquals(messageText, sentMessage.getMessagetext());
    }
}