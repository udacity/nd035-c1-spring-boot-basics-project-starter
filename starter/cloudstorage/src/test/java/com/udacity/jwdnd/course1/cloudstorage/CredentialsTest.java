package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.controller.CredentialPayload;
import com.udacity.jwdnd.course1.cloudstorage.controller.UserPayload;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
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

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CredentialsTest {

    @LocalServerPort
    private int port;

    private WebDriver driver;

    private int userId;

    public final String USERNAME = "testuser";
    private final String PASSWORD = "testpassword";

    @Autowired
    private UserService userService;

    @Autowired
    private CredentialService credentialService;

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

        this.userService.createUser(new UserPayload(USERNAME, PASSWORD, "", ""));
        this.userId = this.userService.getUserId(USERNAME);
        TestHelper.login(this.driver, port, USERNAME, PASSWORD);
    }

    @AfterEach
    public void afterEach() {
        if (this.driver != null) {
            driver.quit();
        }

        // delete all credentials
        this.credentialService.getCredentials(userId).forEach(
            credentialPayload -> this.credentialService.deleteCredential(this.userId, Integer.parseInt(credentialPayload.getCredentialId()))
        );

        // delete user
        this.userService.deleteUser(USERNAME);
    }

    @Test
    public void createCredential() {
        final String CREDENTIAL_URL = "test_url";
        final String CREDENTIAL_USERNAME = "test_username";
        final String CREDENTIAL_PASSWORD = "test_password";

        // add credential
        WebDriverWait wait = new WebDriverWait(driver, 2);
        this.navigateToCredentialsTab();
        this.driver.findElement(By.id("addCredentialBtn")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credentialModal")));
        this.driver.findElement(By.id("credential-url")).sendKeys(CREDENTIAL_URL);
        this.driver.findElement(By.id("credential-username")).sendKeys(CREDENTIAL_USERNAME);
        this.driver.findElement(By.id("credential-password")).sendKeys(CREDENTIAL_PASSWORD);
        this.driver.findElement(By.id("saveCredentialBtn")).click();

        // check result page
        wait.until(ExpectedConditions.titleIs("Result"));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("successMessage")));
        this.navigateToCredentialsTab();

        // check if credential is shown in table
        assertNotNull(this.findCredentialsEntry(CREDENTIAL_URL, CREDENTIAL_USERNAME));
    }

    @Test
    public void editCredential() {
        final String CREDENTIAL_URL = "test_url";
        final String CREDENTIAL_USERNAME = "test_username";
        final String CREDENTIAL_PASSWORD = "test_password";
        final String CREDENTIAL_URL_EDIT = "test_url_edited";
        final String CREDENTIAL_USERNAME_EDIT = "test_username_edited";
        final String CREDENTIAL_PASSWORD_EDIT = "test_password_edited";

        WebDriverWait wait = new WebDriverWait(driver, 2);

        // insert credential into db
        this.credentialService.insertCredential(this.userId, new CredentialPayload("", CREDENTIAL_URL, CREDENTIAL_USERNAME, CREDENTIAL_PASSWORD, ""));

        // find credential
        this.navigateToCredentialsTab();
        WebElement expectedNoteDetail = this.findCredentialsEntry(CREDENTIAL_URL, CREDENTIAL_USERNAME);
        assertNotNull(expectedNoteDetail);

        // edit credential
        expectedNoteDetail.findElement(By.className("edit-credential-button")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credentialModal")));
        this.driver.findElement(By.id("credential-url")).clear();
        this.driver.findElement(By.id("credential-url")).sendKeys(CREDENTIAL_URL_EDIT);
        this.driver.findElement(By.id("credential-username")).clear();
        this.driver.findElement(By.id("credential-username")).sendKeys(CREDENTIAL_USERNAME_EDIT);
        this.driver.findElement(By.id("credential-password")).clear();
        this.driver.findElement(By.id("credential-password")).sendKeys(CREDENTIAL_PASSWORD_EDIT);
        this.driver.findElement(By.id("saveCredentialBtn")).click();

        // check result page
        wait.until(ExpectedConditions.titleIs("Result"));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("successMessage")));
        this.navigateToCredentialsTab();

        // check if credential is shown in table
        assertNotNull(this.findCredentialsEntry(CREDENTIAL_URL_EDIT, CREDENTIAL_USERNAME_EDIT));
    }

    @Test
    public void deleteCredential() {
        final String CREDENTIAL_URL = "test_url";
        final String CREDENTIAL_USERNAME = "test_username";
        final String CREDENTIAL_PASSWORD = "test_password";

        WebDriverWait wait = new WebDriverWait(driver, 2);

        // insert credential into db
        this.credentialService.insertCredential(this.userId, new CredentialPayload("", CREDENTIAL_URL, CREDENTIAL_USERNAME, CREDENTIAL_PASSWORD, ""));

        // find credential
        this.navigateToCredentialsTab();
        WebElement expectedNoteDetail = this.findCredentialsEntry(CREDENTIAL_URL, CREDENTIAL_USERNAME);
        assertNotNull(expectedNoteDetail);

        // delete credential
        expectedNoteDetail.findElement(By.className("delete-credential-button")).click();

        // check result page
        wait.until(ExpectedConditions.titleIs("Result"));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("successMessage")));
        this.navigateToCredentialsTab();

        // check if credential is no longer in table
        assertNull(this.findCredentialsEntry(CREDENTIAL_URL, CREDENTIAL_USERNAME));
    }

    private void navigateToCredentialsTab() {
        WebDriverWait wait = new WebDriverWait(driver, 2);
        this.driver.get("http://localhost:" + this.port + "/");
        this.driver.findElement(By.id("nav-credentials-tab")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-credentials")));
    }

    private WebElement findCredentialsEntry(String url, String username) {
        for (WebElement noteDetail : this.driver.findElements(By.className("credential-detail"))) {
            String credentialUrl = noteDetail.findElement(By.className("credential-url")).getText();
            String credentialsUsername = noteDetail.findElement(By.className("credential-username")).getText();
            if (credentialUrl.equals(url) && credentialsUsername.equals(username)) {
                return noteDetail;
            }
        }
        return null;
    }
}
