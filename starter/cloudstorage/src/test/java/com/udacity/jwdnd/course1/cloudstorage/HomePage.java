package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HomePage {

    @FindBy(id = "logout-button")
    private WebElement logoutButton;

    @FindBy(id = "nav-notes-tab")
    private WebElement notesTab;

    @FindBy(id = "add-note-button")
    private WebElement addNoteButton;

    @FindBy(id = "note-title")
    private WebElement noteTitle;

    @FindBy(id = "note-description")
    private WebElement noteDescription;

    @FindBy(id = "save-note-button")
    private WebElement saveNoteButton;

    @FindBy(id = "btnEditNote")
    private WebElement editNoteButton;

    @FindBy(id = "ancDeleteNote")
    private WebElement deleteButton;

    private WebDriver webDriver;

    public HomePage(WebDriver driver) {
        this.webDriver = driver;
        PageFactory.initElements(driver, this);
    }

    public void goToNotesTab() {
        webDriver.manage().window().maximize();
        notesTab.click();
    }

    public void createNote(String title, String description) throws InterruptedException {
        goToNotesTab();
        Thread.sleep(2000);
        addNoteButton.click();
        Thread.sleep(2000);

        noteTitle.sendKeys(title);
        noteDescription.sendKeys(description);
        saveNoteButton.click();
    }

    public void editNote(String title, String description) throws InterruptedException {
        goToNotesTab();
        Thread.sleep(2000);

        editNoteButton.click();
        Thread.sleep(2000);

        noteTitle.clear();
        noteTitle.sendKeys(title);
        noteDescription.clear();
        noteDescription.sendKeys(description);
        saveNoteButton.click();
        Thread.sleep(2000);
    }

    public void deleteNote() throws InterruptedException {
        goToNotesTab();
        Thread.sleep(2000);

        deleteButton.click();
        Thread.sleep(2000);
    }


    public String getFirstNoteTitle() {
        return webDriver.findElement(By.id("show-note-title")).getText();
    }
    public String getFirstNoteDescription() {
        return webDriver.findElement(By.id("show-note-description")).getText();
    }
    public void logout() {
        logoutButton.click();
    }
}
