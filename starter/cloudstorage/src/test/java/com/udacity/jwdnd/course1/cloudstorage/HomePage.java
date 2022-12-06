package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HomePage {

    @FindBy(id = "logout-button")
    private WebElement logoutButton;

    @FindBy(id = "nav-notes-tab")
    private WebElement notesTab;

    @FindBy(id = "add-note-button")
    private WebElement addNoteButton;

    @FindBy(id = "note-title")
    private WebElement noteTitle;

    @FindBy(id = "note-description")
    private WebElement noteDescription;

    @FindBy(id = "save-note-button")
    private WebElement saveNoteButton;

    @FindBy(id = "btnEditNote")
    private WebElement editNoteButton;

    @FindBy(id = "ancDeleteNote")
    private WebElement noteDeleteButton;

    @FindBy(id = "nav-credentials-tab")
    private WebElement credentialsTab;

    @FindBy(id = "add-credential-button")
    private WebElement addCredentialButton;

    @FindBy(id = "credential-url")
    private WebElement credentialUrl;

    @FindBy(id = "credential-username")
    private WebElement credentialUsername;

    @FindBy(id = "credential-password")
    private WebElement credentialPassword;

    @FindBy(id = "save-cred-button")
    private WebElement saveCredentialButton;

    @FindBy(id = "btnEditCred")
    private WebElement editCredentialsButton;

    @FindBy(id = "btnDeleteCred")
    private WebElement credentialDeleteButton;

    private WebDriver webDriver;
    private final JavascriptExecutor js;
    private final WebDriverWait webDriverWait;

    public HomePage(WebDriver driver) {
        this.webDriver = driver;
        PageFactory.initElements(driver, this);
        js = (JavascriptExecutor)webDriver;
        webDriverWait = new WebDriverWait(driver, 500);
    }

    public void goToNotesTab() {
        webDriver.manage().window().maximize();
        notesTab.click();
    }

    public void createNote(String title, String description) throws InterruptedException {
        goToNotesTab();
        sleep();
        addNoteButton.click();
        sleep();

        noteTitle.sendKeys(title);
        noteDescription.sendKeys(description);
        saveNoteButton.click();
    }

    public void editNote(String title, String description) throws InterruptedException {
        goToNotesTab();
        sleep();

        editNoteButton.click();
        sleep();

        noteTitle.clear();
        noteTitle.sendKeys(title);
        noteDescription.clear();
        noteDescription.sendKeys(description);
        saveNoteButton.click();
        sleep();
    }

    public void deleteNote() throws InterruptedException {
        goToNotesTab();
        sleep();

        noteDeleteButton.click();
        sleep();
    }

    public void goToCredentialsTab() {
        credentialsTab.click();
    }

    public void createCredentials(String url, String username, String password) throws InterruptedException {
        goToCredentialsTab();
        sleep();
        addCredentialButton.click();
        sleep();

        credentialUrl.sendKeys(url);
        credentialUsername.sendKeys(username);
        credentialPassword.sendKeys(password);
        saveCredentialButton.click();
    }

    public void editCredentials(String newUrl, String newUserName, String newPassword) throws InterruptedException {
        goToCredentialsTab();
        sleep();

        editCredentialsButton.click();
        sleep();

        credentialUrl.clear();
        credentialUrl.sendKeys(newUrl);
        credentialUsername.clear();
        credentialUsername.sendKeys(newUserName);
        credentialPassword.clear();
        credentialPassword.sendKeys(newPassword);
        saveCredentialButton.click();
        sleep();
    }

    public void deleteCredential() throws InterruptedException {
        goToCredentialsTab();
        sleep();

        credentialDeleteButton.click();
        sleep();
    }

    public String getFirstNoteTitle() {
        return webDriver.findElement(By.id("show-note-title")).getText();
    }

    public String getFirstNoteDescription() {
        return webDriver.findElement(By.id("show-note-description")).getText();
    }

    public String getFirstCredentialUrl() {
        return webDriver.findElement(By.id("cred-url-title")).getText();
    }

    public String getFirstCredentialUsername() {
        return webDriver.findElement(By.id("cred-username-title")).getText();
    }
    public String getFirstCredentialPassword() {
        return webDriver.findElement(By.id("cred-password-title")).getText();
    }

    public void logout() {
        try {
            js.executeScript("arguments[0].click();", logoutButton);
        } catch (NoSuchElementException e) {
            WebElement logoutButton = webDriver.findElement(By.id("logout-button"));
            js.executeScript("arguments[0].click();", logoutButton);
        }
    }

    public boolean isNotePresent() {
        return isElementPresent(By.id("show-note-title")) && isElementPresent(By.id("show-note-description"));
    }

    public boolean isCredentialsPresent() {
        return isElementPresent(By.id("cred-url-title"))
                && isElementPresent(By.id("cred-username-title"))
                && isElementPresent(By.id("cred-password-title"));
    }

    private boolean isElementPresent(By key) {
        try {
            webDriver.findElement(key);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    private void sleep() throws InterruptedException {
        Thread.sleep(2000);
    }
}