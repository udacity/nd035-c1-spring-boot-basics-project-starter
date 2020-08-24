package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SignupPage {

    @FindBy(id = "inputFirstName")
    private WebElement firstNameField;

    @FindBy(id = "inputLastName")
    private WebElement lastNameField;

    @FindBy(id = "inputUsername")
    private WebElement userNameField;

    @FindBy(id = "inputPassword")
    private WebElement passwordField;

    @FindBy(id = "submit-button")
    private WebElement submitButton;

    @FindBy(id = "error-msg")
    private WebElement errorMessage;

    @FindBy(id ="success-msg")
    private WebElement successMessage;

    public SignupPage(WebDriver driver) {
        PageFactory.initElements(driver, this);

    }

    public void registerUser(String firstName, String lastName, String userName, String password) {
        firstNameField.sendKeys(firstName);
        lastNameField.sendKeys(lastName);
        userNameField.sendKeys(userName);
        passwordField.sendKeys(password);
        submitButton.click();
    }

    public String getSuccessMsg() {
        return successMessage.getText();
    }
    public String getErrorMsg() {
        return errorMessage.getText();
    }
}
