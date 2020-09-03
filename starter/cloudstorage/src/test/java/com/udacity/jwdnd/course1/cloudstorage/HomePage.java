package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;


import java.util.HashMap;
import java.util.Map;

public class HomePage {

    @FindBy(id = "logout-button")
    private WebElement logoutButton;

    @FindBy(id = "fileUpload")
    private WebElement fileUploadField;

    @FindBy(id = "upload-button")
    private WebElement uploadButton;

    @FindBy(id = "download-link")
    private WebElement download;

    @FindBy(id = "delete-link")
    private WebElement delete;

    @FindBy(id = "nav-files-tab")
    private WebElement filesTab;

    @FindBy(id = "nav-notes-tab")
    private WebElement notesTab;

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

    @FindBy(id = "credentialSubmit")
    private WebElement credentialSubmit;

    @FindBy(id = "url-value")
    private WebElement urlValue;

    @FindBy(id = "username-value")
    private WebElement usernameValue;

    @FindBy(id = "password-value")
    private WebElement passwordValue;

    @FindBy(id = "edit-credential")
    private WebElement editCredentialButton;

    public HomePage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public void logout() {
        logoutButton.click();
    }

    public void upload(String fileUrl) {
        fileUploadField.sendKeys(fileUrl);
        uploadButton.click();
    }

    public void delete() {
        delete.click();
    }

    public void addCredential(WebDriver driver, String url, String username, String password) {

        String javascriptValueArgument = "arguments[0].value='%s';";
        String jsClick = "arguments[0].click();";
        String urlValue = String.format(javascriptValueArgument, url);
        String usernameValue = String.format(javascriptValueArgument, username);
        String passwordValue = String.format(javascriptValueArgument, password);

        credentialsTab.click();
        JavascriptExecutor executor = (JavascriptExecutor) driver;

        executor.executeScript(jsClick, addCredentialButton);
        executor.executeScript(urlValue, credentialUrl);
        executor.executeScript(usernameValue, credentialUsername);
        executor.executeScript(passwordValue, credentialPassword);

//        String value = (String) ((JavascriptExecutor) driver).executeScript("return arguments[0].value;",credentialUrl);

        executor.executeScript(jsClick, credentialSubmit);

    }

    public Map<String, String> getCredential(WebDriver driver) {

        Map<String, String> value = new HashMap<>();
        credentialsTab.click();
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        String url = (String) (executor.executeScript("return arguments[0].innerHTML;",urlValue));
        String username = (String) (executor.executeScript("return arguments[0].innerHTML;",usernameValue));
        String password = (String) (executor.executeScript("return arguments[0].innerHTML;",passwordValue));

        value.put("url", url);
        value.put("username", username);
        value.put("password", password);

        return value;
    }

    public void editCredential(WebDriver driver, String url, String username, String password) {
        String javascriptValueArgument = "arguments[0].value='%s';";
        String jsClick = "arguments[0].click();";
        String urlValue = String.format(javascriptValueArgument, url);
        String usernameValue = String.format(javascriptValueArgument, username);
        String passwordValue = String.format(javascriptValueArgument, password);

        credentialsTab.click();
        JavascriptExecutor executor = (JavascriptExecutor) driver;

        executor.executeScript(jsClick, editCredentialButton);
        executor.executeScript(urlValue, credentialUrl);
        executor.executeScript(usernameValue, credentialUsername);
        executor.executeScript(passwordValue, credentialPassword);

        executor.executeScript(jsClick, credentialSubmit);
    }
}
