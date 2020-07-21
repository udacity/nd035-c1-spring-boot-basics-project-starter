package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ResultPageObject {

    public ResultPageObject(WebDriver driver) {
        System.out.println(">>> ResultPageObject");
        PageFactory.initElements(driver, this);
        System.out.println("<<< ResultPageObject");
    }

    @FindBy(id = "savedContinue")
    private WebElement saveContinue;

    public WebElement getSaveContinue() {
        return saveContinue;
    }

    public void setSaveContinue(WebElement saveContinue) {
        this.saveContinue = saveContinue;
    }
}
