package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HomePage {
    @FindBy(id = "logout-button")
    private WebElement logoutButton;

    @FindBy(css="#nav-files-tab")
    private WebElement filesTab;

    @FindBy(id="fileTable")
    private WebElement filesTable;

    @FindBy(id="fileUpload")
    private WebElement fileUpload;

    @FindBy(id="fileUploadButton")
    private WebElement fileUploadButton;

    @FindBy(id="file-download")
    private WebElement fileDownload;

    @FindBy(id="file-delete")
    private WebElement fileDelete;

    @FindBy(css="#nav-notes-tab")
    private WebElement notesTab;

    @FindBy(id="submitNewNoteButton")
    private WebElement addNoteButton;

    @FindBy(id="saveNoteButton")
    private WebElement saveNoteButton;

    @FindBy(css="#note-title")
    private WebElement noteTitleField;

    @FindBy(css="#note-description")
    private WebElement noteDescriptionField;

    @FindBy(id="note-table")
    private WebElement noteTable;

    @FindBy(id="note-edit")
    private WebElement noteEdit;

    @FindBy(id="note-delete")
    private WebElement noteDelete;

    @FindBy(css="#nav-credentials-tab")
    private WebElement credentialsTab;

    @FindBy(id="submitNewCredentialButton")
    private WebElement addCredentialButton;

    @FindBy(id="credentialSubmit")
    private WebElement submitCredentialButton;

    @FindBy(id="credentials-save")
    private WebElement saveCredentialButton;

    @FindBy(css="#credential-url")
    private WebElement credentialURL;

    @FindBy(css="#credential-username")
    private WebElement credentialUsername;

    @FindBy(css="#credential-password")
    private WebElement credentialPassword;

    @FindBy(id="credentialTable")
    private WebElement credentialsTable;

    @FindBy(id="credential-edit")
    private WebElement credentialEdit;

    @FindBy(id="credential-delete")
    private WebElement credentialDelete;

    @FindBy(id="creds-table")
    private WebElement credentialTable;



    private JavascriptExecutor executor;

    public HomePage(WebDriver driver){
        PageFactory.initElements(driver, this);
        executor = (JavascriptExecutor) driver;
    }

    public void logout(WebDriver driver){
        this.logoutButton.submit();
    }

    public void openFilesTab() {
        this.executor.executeScript("arguments[0].click()", filesTab);
    }

    public void uploadFile(String file){
        openFilesTab();
        this.fileUpload.sendKeys(file);
        this.fileUploadButton.click();
    }

    public void downloadFile(String filename){
        openFilesTab();
        this.fileDownload.click();
    }

    public void deleteFile(){
        openFilesTab();
        this.fileDelete.click();
    }

    public boolean isFilePresent(String filename){
        openFilesTab();
        return this.filesTable.getText().contains(filename);
    }

    public void openNotesTab() {
        this.executor.executeScript("arguments[0].click()", notesTab);
    }

    public void submitNote(WebDriverWait wait, String title, String content){
        openNotesTab();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("submitNewNoteButton")));
        this.addNoteButton.click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("note-title")));
        this.noteTitleField.sendKeys(title);
        this.noteDescriptionField.sendKeys(content);
        this.saveNoteButton.click();
    }

    public boolean isNotePresent(String title, String content){
        openNotesTab();
        return this.noteTable.getText().contains(title) &&
                this.noteTable.getText().contains(content);
    }

    public void deleteNote(WebDriverWait wait){
        openNotesTab();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("note-table")));
        this.noteDelete.click();
    }


    public void openCredentialsTab() {
        this.executor.executeScript("arguments[0].click()", credentialsTab);
    }

    public void submitCredential(WebDriverWait wait, String url, String username, String password){
        openCredentialsTab();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("submitNewCredentialButton")));
        this.addCredentialButton.click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-url")));
        this.credentialURL.sendKeys(url);
        this.credentialUsername.sendKeys(username);
        this.credentialPassword.sendKeys(password);
        this.saveCredentialButton.click();
    }

    public boolean isCredentialPresent(String url, String username, String password){
        openCredentialsTab();
        return this.credentialTable.getText().contains(url) &&
                this.credentialTable.getText().contains(username) &&
                !this.credentialTable.getText().contains(password);
    }

    public void deleteCredential(WebDriverWait wait){
        openCredentialsTab();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credentialTable")));
        this.credentialDelete.click();
    }
}
