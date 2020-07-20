package com.udacity.jwdnd.course1.cloudstorage;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class HomeTests {
    @LocalServerPort
    private Integer port;
    private final String USERNAME = "michael777";
    private final String PASSWORD = "whatabadpassword";

    private static WebDriver driver;
    private HomePageObject homePage;
    private LoginPageObject loginPage;
    private SignupPageObject signupPage;
    private ResultPageObject resultPage;
    private final String BASE_URL = "http://localhost:";



    @BeforeAll
    public static void beforeAll() {
    }

    @AfterAll
    public static void afterAll() {
    }

    @BeforeEach
    public void beforeEach() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.get("http://localhost:" + port + "/login");
        loginPage = new LoginPageObject(driver);
    }

    @AfterEach
    public void afterEach() {
        driver.quit();
    }

    @Test
    public void homePageNotAccessibleWhenNotLoggedIn() {
        getHomePage();
        String currentURL = driver.getCurrentUrl();
        assertTrue(currentURL.indexOf("home") == -1);
    }

    @Test
    public void signUpLogInVerifyAccessHomeLogOutNoAccessHomePage() throws InterruptedException {
        signUpAndLogin(USERNAME, PASSWORD);
        String currentURL = driver.getCurrentUrl();
        assertTrue(currentURL.indexOf("home") >= 0);
        getHomePage();
        homePage.getLogOutButton().click();
        currentURL = driver.getCurrentUrl();
        // Make sure we did logout and on the login page
        assertTrue(currentURL.indexOf("login") >= 0);
        driver.get(BASE_URL + port + "/home");
        currentURL = driver.getCurrentUrl();
        assertTrue(currentURL.indexOf("login") >= 0);
    }

    @Test
    public void createNoteAndSeeInListTest() throws InterruptedException {
        signUpAndLogin(USERNAME, PASSWORD);
        createNoteAndSeeInList();

        // Get the list
        Thread.sleep(1000);
        List<WebElement> allTitles = driver.findElements(By.id("noteTitleList"));
        // Verify the item is in the list
        for (WebElement title: allTitles) {
            String theTitle = title.getText();
            assertEquals("This is the title", theTitle);
        }
    }

    @Test
    public void editNoteAndSeeInListTest() throws InterruptedException {
        WebDriverWait wait = new WebDriverWait (driver, 500);
        signUpAndLogin(USERNAME, PASSWORD);
        createNoteAndSeeInList();
        logout(wait);
        Thread.sleep(3000);
        login(USERNAME, PASSWORD);
        Thread.sleep(3000);
        waitAndClick(wait, homePage.getNavNotesTab());
        // Get the first Edit button
        Thread.sleep(3000);
        List<WebElement> allEditNote = driver.findElements(By.id("editNote"));
        WebElement firstEditButton = allEditNote.get(0);
        // Click the Edit Button
        waitAndClick(wait, firstEditButton);
        // Change the Title
        waitOn(wait, homePage.getNoteTitle());
        homePage.getNoteTitle().clear();
        waitAndAddText(wait, homePage.getNoteTitle(), "New title");
        waitAndClick(wait, homePage.getSubmitNoteButtonFooter());
        Thread.sleep(3000);
        waitAndClick(wait, homePage.getSaveContinueOnResult());
        Thread.sleep(1000);
        waitAndClick(wait, homePage.getNavNotesTab());
        // Verify the title has changed
        // Get the list
        Thread.sleep(1000);
        List<WebElement> allTitles = driver.findElements(By.id("noteTitleList"));
        // Verify the item is in the list
        for (WebElement title: allTitles) {
            String theTitle = title.getText();
            assertEquals("New title", theTitle);
        }
    }

    @Test
    public void deleteNoteAndSeeInListTest() throws Exception {
        signUpAndLogin(USERNAME, PASSWORD);
        getHomePage();
        WebDriverWait wait = new WebDriverWait (driver, 500);
        Thread.sleep(3000);
        createNewNoteNavBackToNotesTab(wait, "First Note", "First note description");
        createNewNoteNavBackToNotesTab(wait, "Second Note", "Second note description");
        logout(wait);
        Thread.sleep(3000);
        login(USERNAME, PASSWORD);
        Thread.sleep(3000);
        waitAndClick(wait, homePage.getNavNotesTab());
        // Get the first Edit button
        Thread.sleep(3000);
        List<WebElement> allDeleteNoteButtons = driver.findElements(By.id("deleteNote"));
        int originalSizeBeforeDelete = allDeleteNoteButtons.size();
        WebElement firstDeleteButton = allDeleteNoteButtons.get(0);
        // Click the Edit Button
        waitAndClick(wait, firstDeleteButton);
        Thread.sleep(3000);
        waitAndClick(wait, homePage.getSaveContinueOnResult());
        Thread.sleep(1000);
        waitAndClick(wait, homePage.getNavNotesTab());
        // Verify the title has changed
        // Get the list
        Thread.sleep(3000);
        List<WebElement> allTitles = driver.findElements(By.id("noteTitleList"));
        // Verify the item is in the list
        assertTrue((originalSizeBeforeDelete == (allTitles.size() + 1)));
        String title = allTitles.get(0).getText();
        assertEquals("Second Note", title);
    }

    @Test
    public void createCredentialAndSeeInListTest() throws InterruptedException {
        signUpAndLogin(USERNAME, PASSWORD);
        createCredentialAndSeeInList();

        // Get the list
        Thread.sleep(1000);
        List<WebElement> allUrls = driver.findElements(By.id("credentailURLList"));
        // Verify the item is in the list
        for (WebElement url: allUrls) {
            String theUrl = url.getText();
            assertEquals("http://blah.com", theUrl);
        }
    }

    @Test
    public void editCredentialAndSeeInListTest() throws InterruptedException {
        WebDriverWait wait = new WebDriverWait (driver, 500);
        signUpAndLogin(USERNAME, PASSWORD);
        createCredentialAndSeeInList();
        logout(wait);
        Thread.sleep(3000);
        login(USERNAME, PASSWORD);
        Thread.sleep(3000);
        waitAndClick(wait, homePage.getNavCredentialsTab());
        // Get the first Edit button
        Thread.sleep(3000);
        List<WebElement> allEditCredential = driver.findElements(By.id("editCredential"));
        WebElement firstEditButton = allEditCredential.get(0);
        // Click the Edit Button
        waitAndClick(wait, firstEditButton);
        // Change the Title
        waitOn(wait, homePage.getCredentialUrl());
        homePage.getCredentialUrl().clear();
        waitAndAddText(wait, homePage.getCredentialUrl(), "http://google.com");
        waitAndClick(wait, homePage.getSaveCredentialFooter());
        Thread.sleep(3000);
        waitAndClick(wait, homePage.getSaveContinueOnResult());
        Thread.sleep(1000);
        waitAndClick(wait, homePage.getNavCredentialsTab());
        // Get the list
        Thread.sleep(1000);
        List<WebElement> allURL = driver.findElements(By.id("credentailURLList"));
        // Verify the item is in the list
        for (WebElement url: allURL) {
            String theTitle = url.getText();
            assertEquals("http://google.com", theTitle);
        }
    }

    @Test
    public void deleteCredentialAndSeeInListTest() throws Exception {
        signUpAndLogin(USERNAME, PASSWORD);
        getHomePage();
        WebDriverWait wait = new WebDriverWait (driver, 500);
        Thread.sleep(3000);
        createNewCredentialNavBackToNotesTab(wait, "http://blah.com", "Frank", ")(*SDFNJ");
        createNewCredentialNavBackToNotesTab(wait, "http://somewhere.com", "James", "SDFLKJSD:LFKJ");
        logout(wait);
        Thread.sleep(3000);
        login(USERNAME, PASSWORD);
        Thread.sleep(3000);
        waitAndClick(wait, homePage.getNavCredentialsTab());
        // Get the first Edit button
        Thread.sleep(3000);
        List<WebElement> allDeleteCredentialButtons = driver.findElements(By.id("deleteCredential"));
        int originalSizeBeforeDelete = allDeleteCredentialButtons.size();
        WebElement firstDeleteButton = allDeleteCredentialButtons.get(0);
        // Click the Edit Button
        waitAndClick(wait, firstDeleteButton);
        Thread.sleep(3000);
        waitAndClick(wait, homePage.getSaveContinueOnResult());
        Thread.sleep(1000);
        waitAndClick(wait, homePage.getNavCredentialsTab());
        // Verify the title has changed
        // Get the list
        Thread.sleep(3000);
        List<WebElement> credentailURLList = driver.findElements(By.id("credentailURLList"));
        // Verify the item is in the list
        assertTrue((originalSizeBeforeDelete == (credentailURLList.size() + 1)));
        String url = credentailURLList.get(0).getText();
        assertEquals("http://somewhere.com", url);
    }


    private void createCredentialAndSeeInList() throws InterruptedException {
        WebDriverWait wait = new WebDriverWait (driver, 500);
        getHomePage();

        createNewCredentialNavBackToCredentialsTab(wait, "http://blah.com", "Frank", "Blah2");
    }

    private void createNewCredentialNavBackToCredentialsTab(WebDriverWait wait, String url, String username, String password) throws InterruptedException {
        waitAndClick(wait, homePage.getNavCredentialsTab());
        waitAndClick(wait, homePage.getShowCredentialsModal());
        waitAndAddText(wait, homePage.getCredentialUrl(), url);
        waitAndAddText(wait, homePage.getCredentialUsername(), username);
        waitAndAddText(wait, homePage.getCredentialPassword(), password);
        waitAndClick(wait, homePage.getSaveCredentialFooter());
        Thread.sleep(3000);
        waitAndClick(wait, homePage.getSaveContinueOnResult());
        Thread.sleep(3000);
        waitAndClick(wait, homePage.getNavCredentialsTab());
    }

    private void signUpAndLogin(String userName, String passWord) throws InterruptedException {
        signupPage = new SignupPageObject(driver);
        driver.get(BASE_URL + port + "/signup");

        SignupPageObject signupPage = new SignupPageObject(driver);
        signupPage.signup("Michael", "Schumacher", userName, passWord);
        signupPage.getSubmitButton().click();

        login(userName, passWord);
    }

    private void getHomePage() {
        driver.get(BASE_URL + port + "/home");
        homePage = new HomePageObject(driver);
    }

    private void createNoteAndSeeInList() throws InterruptedException {
        WebDriverWait wait = new WebDriverWait (driver, 500);
        getHomePage();

        createNewNoteNavBackToNotesTab(wait, "This is the title", "This is the description, if real, it would be interesting");
    }

    private void createNewNoteNavBackToNotesTab(WebDriverWait wait, String title, String description) throws InterruptedException {
        waitAndClick(wait, homePage.getNavNotesTab());
        waitAndClick(wait, homePage.getShowNoteModelButton());
        waitAndAddText(wait, homePage.getNoteTitle(), title);
        waitAndAddText(wait, homePage.getNoteDescription(), description);
        waitAndClick(wait, homePage.getSubmitNoteButtonFooter());
        Thread.sleep(3000);
        waitAndClick(wait, homePage.getSaveContinueOnResult());
        Thread.sleep(1000);
        waitAndClick(wait, homePage.getNavNotesTab());
    }

    private void createNewCredentialNavBackToNotesTab(WebDriverWait wait, String url, String username, String password) throws InterruptedException {
        waitAndClick(wait, homePage.getNavCredentialsTab());
        waitAndClick(wait, homePage.getShowCredentialsModal());
        waitAndAddText(wait, homePage.getCredentialUrl(), url);
        waitAndAddText(wait, homePage.getCredentialUsername(), username);
        waitAndAddText(wait, homePage.getCredentialPassword(), password);
        waitAndClick(wait, homePage.getSaveCredentialFooter());
        Thread.sleep(3000);
        waitAndClick(wait, homePage.getSaveContinueOnResult());
        Thread.sleep(1000);
        waitAndClick(wait, homePage.getNavNotesTab());
    }

    private void waitAndClick(WebDriverWait wait, WebElement webElement) {
        wait.until(ExpectedConditions.elementToBeClickable(webElement)).click();
    }

    private void waitOn(WebDriverWait wait, WebElement webElement) {
        wait.until(ExpectedConditions.elementToBeClickable(webElement));
    }

    private void waitAndAddText(WebDriverWait wait, WebElement webElement, String textToAdd) {
        wait.until(ExpectedConditions.elementToBeClickable(webElement));
        sendKeys(webElement, textToAdd);
    }

    private void sendKeys(final WebElement element, final String keys) {
        for (var i = 0; i < keys.length(); i++) {
            element.sendKeys(String.valueOf(keys.charAt(i)));
        }
    }

    private void logout(WebDriverWait wait) throws InterruptedException {
        // Click on the Logout button
        Thread.sleep(5000);
        WebElement logoutButton = homePage.getLogOutButton();
        wait.until(ExpectedConditions.elementToBeClickable(logoutButton)).click();
    }

    private void login(String username, String password) {
        driver.get(BASE_URL + port + "/login");
        LoginPageObject loginPage = new LoginPageObject(driver);
        loginPage.login(username, password);
        loginPage.getSubmitButton().click();
    }
}


