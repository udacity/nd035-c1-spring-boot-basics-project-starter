package com.udacity.jwdnd.course1.cloudstorage.pageobject;

import com.udacity.jwdnd.course1.cloudstorage.testutils.JavascriptEvents;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ResultsPage {

  private final WebDriver driver;
  private final WebDriverWait wait;

  @FindBy(css = ".alert-success a")
  private WebElement successMessageLink;

  public ResultsPage(WebDriver webDriver) {
    this.driver = webDriver;
    this.wait = new WebDriverWait(webDriver, 10);
    PageFactory.initElements(webDriver, this);
  }

  public void clickOnSuccessMessageLink() {
    wait.until(webDriver -> successMessageLink.isDisplayed());
    JavascriptEvents.click(successMessageLink, driver);
    JavascriptEvents.waitForReadyState(driver);
  }
}
