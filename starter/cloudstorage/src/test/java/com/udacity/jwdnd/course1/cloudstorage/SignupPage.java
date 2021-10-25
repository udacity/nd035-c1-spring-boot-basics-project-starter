package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SignupPage {

    @FindBy(id = "inputFirstName")
    private WebElement firstName;

    @FindBy(id = "inputLastName")
    private WebElement lastName;

    @FindBy(id = "inputUsername")
    private WebElement userName;

    @FindBy(id = "inputPassword")
    private WebElement password;

    @FindBy(id = "submit-button")
    private WebElement submitButton;

    @FindBy(id = "login-link")
    private WebElement loginLink;

    @FindBy(id = "success-msg")
    private WebElement successMessage;

    private UserService userService;

    public SignupPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public void registerNewUser(String newFirstName, String newLastName, String newUserName, String newPassword) {
        firstName.sendKeys(newFirstName);
        lastName.sendKeys(newLastName);
        userName.sendKeys(newUserName);
        password.sendKeys(newPassword);
        submitButton.submit();
    }


    public void clickLoginLink() {
        loginLink.click();
    }

    public boolean registrationSuccessShown() {
        return successMessage.isDisplayed();

    }

}
