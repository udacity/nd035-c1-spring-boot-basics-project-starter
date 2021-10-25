package com.udacity.jwdnd.course1.cloudstorage;

import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HomePage {

    @FindBy(id = "logout-button")
    private WebElement logoutButton;

    @FindBy(id = "nav-notes-tab")
    private WebElement notesTabButton;

    @FindBy(id = "nav-notes")
    private WebElement notesTab;

    @FindBy(id = "add-note-button")
    private WebElement addNoteButton;

    @FindBy(id = "note-title")
    private WebElement noteTitle;

    @FindBy(id = "note-description")
    private WebElement noteDescription;

    @FindBy(id = "save-note-button")
    private WebElement saveNoteButton;

    @FindBy(id = "edit-note-button")
    private WebElement editNotesButton;

    @FindBy(id = "delete-note-button")
    private WebElement deleteNoteButton;

    public HomePage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public void navigateToNoteTab(WebDriver driver) {
        WebDriverWait wait = new WebDriverWait(driver, 2);
        wait.until(ExpectedConditions.elementToBeClickable(notesTabButton)).click();
    }

    public void addNote(WebDriver driver, String title, String description) {
        WebDriverWait wait = new WebDriverWait(driver, 2);
        wait.until(ExpectedConditions.elementToBeClickable(addNoteButton)).click();
        wait.until(ExpectedConditions.visibilityOfAllElements(noteTitle, noteDescription, saveNoteButton));
        noteTitle.sendKeys(title);
        noteDescription.sendKeys(description);
        saveNoteButton.click();
    }

    public void assertNewNoteShown(WebDriver driver, String title, String description) {
        WebDriverWait wait = new WebDriverWait(driver, 2);
        wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//*[@id=\"userTable\"]/tbody/tr/th"))));
        String noteTitle = driver.findElement(By.xpath("//*[@id=\"userTable\"]/tbody/tr/th")).getText();
        String noteDescription = driver.findElement(By.xpath("//*[@id=\"userTable\"]/tbody/tr/td[2]")).getText();
        Assertions.assertEquals(title, noteTitle);
        Assertions.assertEquals(description, noteDescription);
    }

    public void editNote(Integer noteNumber, String title, String description) {
        editNotesButton.click();
        noteTitle.sendKeys(title);
        noteDescription.sendKeys(description);
        saveNoteButton.click();
    }

    public void deleteNote() {
        deleteNoteButton.click();
    }

}
