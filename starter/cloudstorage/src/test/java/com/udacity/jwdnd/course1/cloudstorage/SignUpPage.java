package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SignUpPage {

    @FindBy(id = "inputFirstName")
    private WebElement inputFirstName;

    @FindBy(id = "inputLastName")
    private WebElement inputLastName;

    @FindBy(id = "inputUsername")
    private WebElement inputUsername;

    @FindBy(id = "inputPassword")
    private WebElement inputPassword;

    @FindBy(id = "buttonSignUp")
    private WebElement buttonSignUp;

    @FindBy(id = "back-to-login")
    private WebElement backToLogin;

    public SignUpPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public void signupNewUser(String name, String lastname, String username, String password) {
        inputFirstName.sendKeys(name);
        inputLastName.sendKeys(lastname);
        inputUsername.sendKeys(username);
        inputPassword.sendKeys(password);

        buttonSignUp.click();
    }

    public void goToLoginPage(WebDriver driver) {
        String url = backToLogin.getAttribute("href");
        driver.get(url);
    }
}