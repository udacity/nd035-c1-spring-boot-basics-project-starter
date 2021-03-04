package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class Login {
    @FindBy(id = "inputUsername")
    private WebElement inputUsername;

    @FindBy(id = "inputPassword")
    private WebElement inputPassword;

    @FindBy(id = "login-button")
    private WebElement loginButton;

    @FindBy(id="signup-link")
    private WebElement singupLink;

    @FindBy(id="error-msg")
    private WebElement errormsg;

    @FindBy(id="logout-msg")
    private WebElement logoutmsg;

    public Login(WebDriver webDriver){
        PageFactory.initElements(webDriver, this);
    }

    public void login(String inputUsername, String inputPassword) {
        this.inputUsername.sendKeys(inputUsername);
        this.inputPassword.sendKeys(inputPassword);
        this.loginButton.submit();
    }

    public String getErrorMsg(WebDriver driver){
        return this.errormsg.getText();
    }

    public String getLogoutMsg(WebDriver driver){
        return this.logoutmsg.getText();
    }

}
