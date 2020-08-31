package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HomePage {

    @FindBy(id = "logout-button")
    private WebElement logoutButton;

    @FindBy(id = "fileUpload")
    private WebElement fileUploadField;

    @FindBy(id = "upload-button")
    private WebElement uploadButton;

    @FindBy(id = "download-link")
    private WebElement download;

    @FindBy(id = "delete-link")
    private WebElement delete;

    public HomePage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public void logout() {
        logoutButton.click();
    }

    public void upload(String fileUrl) {
        fileUploadField.sendKeys(fileUrl);
        uploadButton.click();
    }

    public void delete() {
        delete.click();
    }
}
