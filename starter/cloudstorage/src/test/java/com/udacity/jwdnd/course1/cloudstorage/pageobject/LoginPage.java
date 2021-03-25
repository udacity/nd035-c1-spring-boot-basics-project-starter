package com.udacity.jwdnd.course1.cloudstorage.pageobject;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {

  public static final String urlPath = "/login";

  @FindBy(name = "username")
  private WebElement username;

  @FindBy(name = "password")
  private WebElement password;

  @FindBy(css = "button[type=submit]")
  private WebElement submitButton;

  @FindBy(css = ".alert-danger")
  private WebElement errorMessage;

  public LoginPage(WebDriver webDriver) {
    PageFactory.initElements(webDriver, this);
  }

  public void fillUsername(String value) {
    username.sendKeys(value);
  }

  public void fillPwd(String value) {
    password.sendKeys(value);
  }

  public void submitLoginForm() {
    submitButton.click();
  }

  public String getErrorMessage() {
    return errorMessage.getText();
  }

  public void loginUser(String username, String pwd) {
    fillUsername(username);
    fillPwd(pwd);
    submitLoginForm();
  }
}
