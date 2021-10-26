package com.udacity.jwdnd.course1.cloudstorage;

import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class HomePage {

    @FindBy(id = "logout-button")
    private WebElement logoutButton;

    @FindBy(id = "nav-notes-tab")
    private WebElement notesTabButton;

    @FindBy(id = "nav-notes")
    private WebElement notesTab;

    @FindBy(id = "notesTable")
    private WebElement notesTable;

    @FindBy(id = "add-note-button")
    private WebElement addNoteButton;

    @FindBy(id = "note-title")
    private WebElement noteTitle;

    @FindBy(id = "note-description")
    private WebElement noteDescription;

    @FindBy(id = "save-note-button")
    private WebElement saveNoteButton;

    @FindBy(id = "edit-note-button")
    private WebElement editNoteButton;

    @FindBy(className = "btn btn-danger")
    private List<WebElement> deleteButtons;

    @FindBy(className = "noteTitleCell")
    private List<WebElement> noteTitles;

    @FindBy(id = "delete-note-button")
    private List<WebElement> deleteNoteButtons;

    @FindBy(id = "empty-notes-text")
    private WebElement emptyNotesText;

    @FindBy(id = "nav-credentials-tab")
    private WebElement credentialsTabButton;

    @FindBy(id = "nav-credentials")
    private WebElement credentialsTab;

    @FindBy(id = "credentialTable")
    private WebElement credentialTable;

    @FindBy(id = "add-credentials-button")
    private WebElement addCredentialsButton;

    @FindBy(id = "credential-url")
    private WebElement credentialUrl;

    @FindBy(id = "credential-username")
    private WebElement credentialUsername;

    @FindBy(id = "credential-password")
    private WebElement credentialPassword;

    @FindBy(id = "save-credential-button")
    private WebElement saveCredentialsButton;

    @FindBy(id = "credential-edit-button")
    private WebElement editCredentialsButton;

    @FindBy(id = "delete-credentials-button")
    private List<WebElement> deleteCredentialsButtons;

    @FindBy(id = "credentials-empty-table")
    private WebElement emptyCredentialsText;

    @FindBy(className = "credentialUrl")
    private List<WebElement> credentialsUrls;

    private WebDriver driver;

    public HomePage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }

    public void logout() {
        WebDriverWait wait = new WebDriverWait(driver, 2);
        wait.until(ExpectedConditions.elementToBeClickable(logoutButton)).click();
    }

    public void navigateToNoteTab() {
        WebDriverWait wait = new WebDriverWait(driver, 2);
        wait.until(ExpectedConditions.elementToBeClickable(notesTabButton)).click();
    }

    public void addNote(String title, String description) {
        WebDriverWait wait = new WebDriverWait(driver, 2);
        wait.until(ExpectedConditions.elementToBeClickable(addNoteButton)).click();
        wait.until(ExpectedConditions.visibilityOfAllElements(noteTitle, noteDescription, saveNoteButton));
        noteTitle.sendKeys(title);
        noteDescription.sendKeys(description);
        saveNoteButton.click();
    }

    public void assertNewNoteShown(String title, String description) {
        WebDriverWait wait = new WebDriverWait(driver, 2);
        wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//*[@id=\"notesTable\"]/tbody/tr/th"))));
        String noteTitle = driver.findElement(By.xpath("//*[@id=\"notesTable\"]/tbody/tr/th")).getText();
        String noteDescription = driver.findElement(By.xpath("//*[@id=\"notesTable\"]/tbody/tr/td[2]")).getText();
        Assertions.assertEquals(title, noteTitle);
        Assertions.assertEquals(description, noteDescription);
    }

    public void editNote(String title, String description) {
        WebDriverWait wait = new WebDriverWait(driver, 2);
        wait.until(ExpectedConditions.elementToBeClickable(editNoteButton)).click();
        wait.until(ExpectedConditions.visibilityOfAllElements(noteTitle, noteDescription, saveNoteButton));
        noteTitle.clear();
        noteTitle.sendKeys(title);
        noteDescription.clear();
        noteDescription.sendKeys(description);
        saveNoteButton.click();
    }

    public void deleteNote(String title) {
        WebDriverWait wait = new WebDriverWait(driver, 2);
        wait.until(ExpectedConditions.visibilityOf(notesTable));

        for (int i=0; i<noteTitles.size(); i++) {
            if (noteTitles.get(i).getText().equals(title)) {
                deleteNoteButtons.get(i).click();
            }
        }
    }

    public void assertNoteDeleted(String title) {
        WebDriverWait wait = new WebDriverWait(driver, 2);
        wait.until(ExpectedConditions.visibilityOf(notesTab));
        for (WebElement note : noteTitles) {
            Assertions.assertNotSame(note.getText(), title);
        }
    }

    public void navigateToCredentialsTab() {
        WebDriverWait wait = new WebDriverWait(driver, 2);
        wait.until(ExpectedConditions.elementToBeClickable(credentialsTabButton)).click();
    }

    public void addCredential(String url, String username, String password) {
        WebDriverWait wait = new WebDriverWait(driver, 2);
        wait.until(ExpectedConditions.elementToBeClickable(addCredentialsButton)).click();
        wait.until(ExpectedConditions.visibilityOfAllElements(credentialUrl, credentialUsername, credentialPassword));
        credentialUrl.sendKeys(url);
        credentialUsername.sendKeys(username);
        credentialPassword.sendKeys(password);
        saveCredentialsButton.click();
    }

    public void assertNewCredentialShown(String url, String username, String password) {
        WebDriverWait wait = new WebDriverWait(driver, 2);
        wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//*[@id=\"credentialTable\"]/tbody/tr/th"))));
        String currentUrl = driver.findElement(By.xpath("//*[@id=\"credentialTable\"]/tbody/tr/th")).getText();
        String currentUsername = driver.findElement(By.xpath("//*[@id=\"credentialTable\"]/tbody/tr/td[2]")).getText();
        String currentPassword = driver.findElement(By.xpath("//*[@id=\"credentialTable\"]/tbody/tr/td[3]")).getText();
        Assertions.assertEquals(currentUrl, url);
        Assertions.assertEquals(currentUsername, username);
        Assertions.assertNotEquals(currentPassword, password);
    }

    public void editCredential(String url, String username, String password) {
        WebDriverWait wait = new WebDriverWait(driver, 2);
        wait.until(ExpectedConditions.elementToBeClickable(editCredentialsButton)).click();
        wait.until(ExpectedConditions.visibilityOfAllElements(credentialUrl, credentialUsername, credentialPassword));
        credentialUrl.clear();
        credentialUrl.sendKeys(url);
        credentialUsername.clear();
        credentialUsername.sendKeys(username);
        credentialPassword.clear();
        credentialPassword.sendKeys(password);
        saveCredentialsButton.click();
    }

    public void deleteCredential(String url) {
        WebDriverWait wait = new WebDriverWait(driver, 2);
        wait.until(ExpectedConditions.visibilityOf(credentialsTab));
        for (int i=0; i<credentialsUrls.size(); i++) {
            if (credentialsUrls.get(i).getText().equals(url)) {
                deleteCredentialsButtons.get(i).click();
            }
        }
    }

    public void assertCredentialDeleted(String url) {
        WebDriverWait wait = new WebDriverWait(driver, 2);
        wait.until(ExpectedConditions.visibilityOf(credentialsTab));
        for (WebElement credentialUrl : credentialsUrls) {
            Assertions.assertNotSame(credentialUrl.getText(), url);
        }
    }
}
