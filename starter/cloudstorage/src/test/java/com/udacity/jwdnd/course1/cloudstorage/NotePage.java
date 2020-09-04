package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.HashMap;
import java.util.Map;

public class NotePage {

    @FindBy(id = "nav-notes-tab")
    private WebElement notesTab;

    @FindBy(id = "add-note-button")
    private WebElement addNoteButton;

    @FindBy(id = "edit-note-button")
    private WebElement editNoteButton;

    @FindBy(id = "delete-note-link")
    private WebElement deleteNoteLink;

    @FindBy(id = "note-title-text")
    private WebElement noteTitleText;

    @FindBy(id = "note-desc-text")
    private WebElement noteDescText;

    @FindBy(id = "note-title")
    private WebElement noteTitleField;

    @FindBy(id = "note-description")
    private WebElement noteDescriptionField;

    @FindBy(id = "noteSubmit")
    private WebElement noteSubmitButton;


    public NotePage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public void addNote(WebDriver driver, String noteTitle, String noteDescription) {

        String javascriptValueArgument = "arguments[0].value='%s';";
        String jsClick = "arguments[0].click();";
        Map<String, String> value = initJavascriptFields(noteTitle, noteDescription, javascriptValueArgument);

        String noteTitleValue = value.get("title");
        String noteDescValue = value.get("desc");

        notesTab.click();

        JavascriptExecutor executor = (JavascriptExecutor) driver;

        executor.executeScript(jsClick, addNoteButton);
        executor.executeScript(noteTitleValue, noteTitleField);
        executor.executeScript(noteDescValue, noteDescriptionField);

        executor.executeScript(jsClick, noteSubmitButton);

    }

    public Map<String, String> getNote(WebDriver driver) {

        Map<String, String> value = new HashMap<>();

        // navigate to note tab
        notesTab.click();

        String javascriptInnerHtml = "return arguments[0].innerHTML;";

        // provides a way to execute JavaScript in Selenium Webdriver
        JavascriptExecutor executor = (JavascriptExecutor) driver;

        String title = (String) (executor.executeScript(javascriptInnerHtml,noteTitleText));
        String description = (String) (executor.executeScript(javascriptInnerHtml,noteDescText));

        value.put("title", title);
        value.put("desc", description);

        return value;


    }

    public void editNote(WebDriver driver, String noteTitle, String noteDescription) {
        String javascriptValueArgument = "arguments[0].value='%s';";
        String jsClick = "arguments[0].click();";
        Map<String, String> value = initJavascriptFields(noteTitle, noteDescription, javascriptValueArgument);

        String noteTitleValue = value.get("title");
        String noteDescValue = value.get("desc");

        notesTab.click();

        JavascriptExecutor executor = (JavascriptExecutor) driver;

        executor.executeScript(jsClick, editNoteButton);
        executor.executeScript(noteTitleValue, noteTitleField);
        executor.executeScript(noteDescValue, noteDescriptionField);
        executor.executeScript(jsClick, noteSubmitButton);

    }

    public void deleteNote(WebDriver driver) {
        String jsClick = "arguments[0].click();";
        notesTab.click();
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        executor.executeScript(jsClick, deleteNoteLink);
    }

    public Map<String, String> initJavascriptFields(String noteTitle, String noteDesc, String jsArgument) {

        Map<String, String> value = new HashMap<>();
        String noteTitleValue = String.format(jsArgument, noteTitle);
        String noteDescValue = String.format(jsArgument, noteDesc);

        value.put("title", noteTitleValue);
        value.put("desc", noteDescValue);

        return value;

    }
}
