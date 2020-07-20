package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SignupPageObject {

    @FindBy(id = "inputFirstName")
    private WebElement inputFirstName;

    @FindBy(id = "inputLastName")
    private WebElement inputLastName;

    @FindBy(id = "inputUsername")
    private WebElement inputUsername;

    @FindBy(id = "inputPassword")
    private WebElement inputPassword;

    @FindBy(id = "signUpButton")
    private WebElement submitButton;

    @FindBy(id = "login-link")
    private WebElement loginLink;

    @FindBy(id = "error-msg")
    private WebElement errorMsg;

    public void signup(String firstName, String lastName, String username, String password) {
        inputFirstName.sendKeys(firstName);
        inputLastName.sendKeys(lastName);
        inputUsername.sendKeys(username);
        inputPassword.sendKeys(password);
    }

    public SignupPageObject(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public WebElement getInputFirstName() {
        return inputFirstName;
    }

    public void setInputFirstName(WebElement inputFirstName) {
        this.inputFirstName = inputFirstName;
    }

    public WebElement getInputLastName() {
        return inputLastName;
    }

    public void setInputLastName(WebElement inputLastName) {
        this.inputLastName = inputLastName;
    }

    public WebElement getInputUsername() {
        return inputUsername;
    }

    public void setInputUsername(WebElement inputUsername) {
        this.inputUsername = inputUsername;
    }

    public WebElement getInputPassword() {
        return inputPassword;
    }

    public void setInputPassword(WebElement inputPassword) {
        this.inputPassword = inputPassword;
    }

    public WebElement getSubmitButton() {
        return submitButton;
    }

    public void setSubmitButton(WebElement submitButton) {
        this.submitButton = submitButton;
    }

    public WebElement getLoginLink() {
        return loginLink;
    }

    public void setLoginLink(WebElement loginLink) {
        this.loginLink = loginLink;
    }

    public WebElement getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(WebElement errorMsg) {
        this.errorMsg = errorMsg;
    }
}
