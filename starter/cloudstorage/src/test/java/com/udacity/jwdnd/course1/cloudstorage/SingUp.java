package com.udacity.jwdnd.course1.cloudstorage;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SingUp {
    @FindBy(name = "firstName")
    private WebElement firstname;

    @FindBy(name = "lastName")
    private WebElement lastname;

    @FindBy(name = "username")
    private WebElement username;

    @FindBy(name = "password")
    private WebElement password;

    @FindBy(id = "submit-button")
    private WebElement submitButton;

    @FindBy(id = "error-msg")
    private WebElement errormessage;

    @FindBy(id="success-msg")
    private WebElement successmessage;

    public SingUp(WebDriver webDriver){
        PageFactory.initElements(webDriver, this);
    }


    public void SingUp(String firstname, String lastname, String username, String password) {
        this.firstname.sendKeys(firstname);
        this.lastname.sendKeys(lastname);
        this.username.sendKeys(username);
        this.password.sendKeys(password);
        this.submitButton.click();
    }

    public String getSuccessMsg(WebDriver driver) {
        return this.successmessage.getText();
    }

    public String getErrorMsg(WebDriver driver) {
        return this.errormessage.getText();
    }
}
