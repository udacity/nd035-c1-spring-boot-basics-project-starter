package com.udacity.jwdnd.course1.cloudstorage.pageobject;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.testutils.JavascriptEvents;
import java.util.List;
import java.util.stream.Collectors;
import lombok.val;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HomePage {
  public static final String urlPath = "/home";
  private final WebDriverWait wait;
  private final WebDriver driver;

  @FindBy(id = "nav-notes-tab")
  private WebElement notesTab;

  @FindBy(css = "#nav-notes .btn-info")
  private WebElement createNoteButton;

  @FindBy(name = "noteTitle")
  private WebElement noteTitle;

  @FindBy(name = "noteDescription")
  private WebElement noteDescription;

  @FindBy(css = "#noteModal button.btn-primary")
  private WebElement noteModalSubmitButton;

  @FindBy(id = "notesTable")
  private WebElement notesTable;

  @FindBy(css = "table#notesTable tbody tr")
  private List<WebElement> notes;

  @FindBy(id = "noteModal")
  private WebElement noteModal;

  @FindBy(id = "nav-credentials-tab")
  private WebElement credsTab;

  @FindBy(css = "#nav-credentials .btn-info")
  private WebElement createCredsButton;

  @FindBy(name = "username")
  private WebElement credsUsername;

  @FindBy(name = "password")
  private WebElement credsPwd;

  @FindBy(name = "url")
  private WebElement credsUrl;

  @FindBy(css = "#credentialModal button.btn-primary")
  private WebElement credsModalSubmitButton;

  @FindBy(id = "credentialTable")
  private WebElement credsTable;

  @FindBy(css = "table#credentialTable tbody tr")
  private List<WebElement> creds;

  @FindBy(id = "credentialModal")
  private WebElement credsModal;

  public HomePage(WebDriver webDriver) {
    this.driver = webDriver;
    this.wait = new WebDriverWait(webDriver, 10);
    PageFactory.initElements(webDriver, this);
  }

  public void openNotes() {
    JavascriptEvents.click(notesTab, driver);
    wait.until(webDriver -> notesTable.isDisplayed());
  }

  public void openNoteModal() {
    JavascriptEvents.click(createNoteButton, driver);
    wait.until((webDriver) -> noteModal.isDisplayed());
  }

  public void fillNoteTitle(String title) {
    noteTitle.sendKeys(title);
  }

  public void fillNoteDescription(String desc) {
    noteDescription.sendKeys(desc);
  }

  public void submitNoteModal() {
    JavascriptEvents.click(noteModalSubmitButton, driver);
  }

  public List<Note> getNotes() {
    return notes.stream()
        .map(
            note -> {
              val desc = note.findElement(By.className("noteDescription")).getText();
              val title = note.findElement(By.className("noteTitle")).getText();
              return Note.builder().noteDescription(desc).noteTitle(title).build();
            })
        .collect(Collectors.toUnmodifiableList());
  }

  public void openEditModalForNote(String title) {
    val note =
        notes.stream()
            .filter(
                noteElement ->
                    noteElement.findElement(By.className("noteTitle")).getText().equals(title))
            .findFirst();
    JavascriptEvents.click(note.get().findElement(By.className("btn-success")), driver);
    wait.until((webDriver) -> noteModal.isDisplayed());
  }

  public void deleteNote(String title) {
    val note =
        notes.stream()
            .filter(
                noteElement ->
                    noteElement.findElement(By.className("noteTitle")).getText().equals(title))
            .findFirst();
    JavascriptEvents.click(note.get().findElement(By.className("btn-danger")), driver);
  }

  public void openCreds() {
    JavascriptEvents.click(credsTab, driver);
    wait.until(webDriver -> credsTable.isDisplayed());
  }

  public void openCredsModal() {
    JavascriptEvents.click(createCredsButton, driver);
    wait.until((webDriver) -> credsModal.isDisplayed());
  }

  public void fillCredsUsername(String username) {
    credsUsername.sendKeys(username);
  }

  public void fillCredsPwd(String password) {
    credsPwd.sendKeys(password);
  }

  public void fillCredsUrl(String url) {
    credsUrl.sendKeys(url);
  }

  public void submitCredsModal() {
    JavascriptEvents.click(credsModalSubmitButton, driver);
  }

  public List<Credential> getCreds() {
    return creds.stream()
        .map(
            cred -> {
              val url = cred.findElement(By.className("credsUrl")).getText();
              val username = cred.findElement(By.className("credsUsername")).getText();
              val pwd = cred.findElement(By.className("credsPwd")).getText();
              return Credential.builder().username(username).password(pwd).url(url).build();
            })
        .collect(Collectors.toUnmodifiableList());
  }
}
