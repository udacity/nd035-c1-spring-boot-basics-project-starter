package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ResultPage {

    @FindBy(id = "successMessage")
    private WebElement successMessage;

    @FindBy(id = "errorMessage")
    private WebElement errorMessage;

    public ResultPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public void assertResultSuccessAndClickContinue(WebDriver driver) {
        WebDriverWait wait = new WebDriverWait(driver, 2);
        wait.until(ExpectedConditions.visibilityOfAllElements(successMessage));

        successMessage.click();
    }


}
