package com.udacity.jwdnd.course1.cloudstorage.testutils;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

public class JavascriptEvents {
  public static void waitForReadyState(WebDriver driver) {
    JavascriptExecutor javascriptExecutor = (JavascriptExecutor) driver;
    new WebDriverWait(driver, 10)
        .until(
            webDriver ->
                ((JavascriptExecutor) webDriver)
                    .executeScript("return document.readyState")
                    .equals("complete"));
  }

  public static void click(WebElement element, WebDriver driver) {
    JavascriptExecutor javascriptExecutor = (JavascriptExecutor) driver;
    javascriptExecutor.executeScript("arguments[0].click()", element);
  }
}
