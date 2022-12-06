package com.udacity.jwdnd.course1.cloudstorage;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SuperDriveCredentialTests {

    @LocalServerPort
    public int port;
    public static WebDriver driver;
    public String baseUrl;
    private HomePage homePage;
    private SignUpPage signUpPage;
    private LoginPage loginPage;

    @BeforeAll
    public static void beforeAll() {
        WebDriverManager.firefoxdriver().setup();
        driver = new FirefoxDriver();
    }

    @BeforeEach
    public void beforeEach() {
        baseUrl = "http://localhost:" + port;
        homePage = new HomePage(driver);
        signUpPage = new SignUpPage(driver);
        loginPage = new LoginPage(driver);
    }

    @AfterAll
    public static void afterAll() {
        driver.quit();
        driver = null;
    }

    @Test
    public void whenCredentialsCreated_thenCredentialsDisplayedAndPasswordEncrypted() throws InterruptedException {
        signupAndLogin("PocketWatch", "12345");

        // Go to home page
        driver.get(baseUrl + "/home");
        WebDriverWait webDriverWait = new WebDriverWait(driver, 3);
        WebElement homeMarker = webDriverWait.until(webDriver -> webDriver.findElement(By.id("nav-files-tab")));
        assertNotNull(homeMarker);

        createCredentials("url1", "user1", "password");

        // Check credential added
        assertEquals("url1", homePage.getFirstCredentialUrl());
        assertEquals("user1", homePage.getFirstCredentialUsername());
        assertEquals("password", homePage.getFirstCredentialPassword());

        homePage.deleteCredential();
        homePage.logout();

        // TODO: Verify displayed password is encrypted
    }

    @Test
    public void givenExistingCredentialsWithPasswordUnencrypted_whenEditing_thenChangesDisplayed() throws InterruptedException {
        // Write a test that views an existing set of credentials,
        // verifies that the viewable password is unencrypted,
        // edits the credentials, and verifies that the changes are displayed.

        signupAndLogin("pocket2017", "watch");
        createCredentials("pocket/watch", "pocket1234", "liver");

        // Check credential added
        assertEquals("pocket/watch", homePage.getFirstCredentialUrl());
        assertEquals("pocket1234", homePage.getFirstCredentialUsername());
        assertEquals("liver", homePage.getFirstCredentialPassword());

        editCredentials("otherUrl", "otherUsername", "otherPassword");

        // Check new credentials
        assertEquals("otherUrl", homePage.getFirstCredentialUrl());
        assertEquals("otherUsername", homePage.getFirstCredentialUsername());
        assertEquals("otherPassword", homePage.getFirstCredentialPassword());

        homePage.deleteCredential();
        homePage.logout();
    }

    @Test
    public void whenCredentialsDeleted_credentialsNotDisplayed() throws InterruptedException {
        // Write a test that deletes an existing set of credentials and
        // verifies that the credentials are no longer displayed.

        signupAndLogin("pocket2017", "watch");
        createCredentials("pocket/watch", "pocket1234", "liver");

        deleteCredentials();
        assertFalse(homePage.isCredentialsPresent());

        homePage.logout();
    }

    private void createCredentials(String url, String username, String password) throws InterruptedException {
        homePage.createCredentials(url, username, password);
        Thread.sleep(2000);
        homePage.goToCredentialsTab();
    }

    private void editCredentials(String newUrl, String newUsername, String newPassword) throws InterruptedException {
        homePage.editCredentials(newUrl, newUsername, newPassword);
        Thread.sleep(2000);
        homePage.goToCredentialsTab();
    }

    private void deleteCredentials() throws InterruptedException {
        homePage.deleteCredential();
        Thread.sleep(2000);
        homePage.goToCredentialsTab();
    }

    private void signupAndLogin(String userName, String password) {
        driver.get(baseUrl + "/signup");
        signUpPage.signupNewUser("Pocket", "Dal", userName, password);

        driver.get(baseUrl + "/login");
        loginPage.loginUser(userName, password);
    }
}
