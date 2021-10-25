package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {

    @FindBy(id = "error-msg")
    private WebElement errorMessage;

    @FindBy(id = "logout-msg")
    private WebElement logoutMessage;

    @FindBy(id = "inputUsername")
    private WebElement userName;

    @FindBy(id = "inputPassword")
    private WebElement password;

    @FindBy(id = "submit-button")
    private WebElement submitButton;

    @FindBy(id = "signup-link")
    private WebElement signupLink;

    public LoginPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public void loginUser(String loginUserName, String loginPassword) {
        userName.sendKeys(loginUserName);
        password.sendKeys(loginPassword);
        submitButton.submit();
    }
}
