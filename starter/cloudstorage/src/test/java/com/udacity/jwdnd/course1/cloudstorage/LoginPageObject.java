package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPageObject {

    @FindBy(id = "inputUsername")
    private WebElement inputUserName;

    @FindBy(id = "inputPassword")
    private WebElement inputPassword;

    @FindBy(id = "submit-button")
    private WebElement submitButton;

    @FindBy(id = "signup-link")
    private WebElement signupLinkButton;

    @FindBy(id = "error-msg")
    private WebElement errorMsg;

    public void login(String username, String password) {
        getInputUserName().sendKeys(username);
        getInputPassword().sendKeys(password);
    }

    public LoginPageObject(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public WebElement getInputUserName() {
        return inputUserName;
    }

    public void setInputUserName(WebElement inputUserName) {
        this.inputUserName = inputUserName;
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

    public WebElement getSignupLinkButton() {
        return signupLinkButton;
    }

    public void setSignupLinkButton(WebElement signupLinkButton) {
        this.signupLinkButton = signupLinkButton;
    }

    public WebElement getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(WebElement errorMsg) {
        this.errorMsg = errorMsg;
    }
}
