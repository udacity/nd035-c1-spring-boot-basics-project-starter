package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ResultPage {

    @FindBy(id = "success-msg")
    private WebElement successMsg;

    @FindBy(id = "error-msg")
    private WebElement errorMsg;

    public ResultPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public String getSuccessMsg() {
        return successMsg.getText();
    }

    public String getErrorMsg() {
        return errorMsg.getText();
    }
}
