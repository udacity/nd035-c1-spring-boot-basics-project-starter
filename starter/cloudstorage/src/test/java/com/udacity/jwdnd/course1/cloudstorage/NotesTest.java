package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.controller.NotePayload;
import com.udacity.jwdnd.course1.cloudstorage.controller.UserPayload;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class NotesTest {

    @LocalServerPort
    private int port;

    private WebDriver driver;

    private int userId;

    public final String USERNAME = "testuser";
    private final String PASSWORD = "testpassword";

    @Autowired
    private UserService userService;

    @Autowired
    private NoteService noteService;

    @BeforeAll
    static void beforeAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    public void beforeEach() {
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.setPageLoadStrategy(PageLoadStrategy.EAGER);
        chromeOptions.setHeadless(true);
        this.driver = new ChromeDriver(chromeOptions);

        this.userService.createUser(new UserPayload(USERNAME, PASSWORD, "", ""));
        this.userId = this.userService.getUserId(USERNAME);
        TestHelper.login(this.driver, port, USERNAME, PASSWORD);
    }

    @AfterEach
    public void afterEach() {
        if (this.driver != null) {
            driver.quit();
        }

        // delete all notes
        this.noteService.getNotes(userId).forEach(
            notePayload -> this.noteService.deleteNote(this.userId, Integer.parseInt(notePayload.getNoteId()))
        );

        // delete user
        this.userService.deleteUser(USERNAME);
    }

    @Test
    public void createNote() {
        final String NOTE_TITLE = "test_title";
        final String NOTE_DESCRIPTION = "test_description";

        // add note
        WebDriverWait wait = new WebDriverWait(driver, 2);
        this.navigateToNotesTab();
        this.driver.findElement(By.id("addNoteBtn")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("noteModal")));
        this.driver.findElement(By.id("note-title")).sendKeys(NOTE_TITLE);
        this.driver.findElement(By.id("note-description")).sendKeys(NOTE_DESCRIPTION);
        this.driver.findElement(By.id("saveNoteBtn")).click();

        // check result page
        wait.until(ExpectedConditions.titleIs("Result"));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("successMessage")));
        this.navigateToNotesTab();

        // check if note is shown in table
        assertNotNull(this.findNoteEntry(NOTE_TITLE, NOTE_DESCRIPTION));
    }

    @Test
    public void editNote() {
        final String NOTE_TITLE = "test_title";
        final String NOTE_DESCRIPTION = "test_description";
        final String NOTE_TITLE_EDIT = "test_title_edited";
        final String NOTE_DESCRIPTION_EDIT = "test_description_edited";

        WebDriverWait wait = new WebDriverWait(driver, 2);

        // insert note into db
        this.noteService.insertNote(this.userId, new NotePayload("", NOTE_TITLE, NOTE_DESCRIPTION));

        // find note
        this.navigateToNotesTab();
        WebElement expectedNoteDetail = this.findNoteEntry(NOTE_TITLE, NOTE_DESCRIPTION);
        assertNotNull(expectedNoteDetail);

        // edit note
        expectedNoteDetail.findElement(By.className("edit-note-button")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("noteModal")));
        this.driver.findElement(By.id("note-title")).clear();
        this.driver.findElement(By.id("note-title")).sendKeys(NOTE_TITLE_EDIT);
        this.driver.findElement(By.id("note-description")).clear();
        this.driver.findElement(By.id("note-description")).sendKeys(NOTE_DESCRIPTION_EDIT);
        this.driver.findElement(By.id("saveNoteBtn")).click();

        // check result page
        wait.until(ExpectedConditions.titleIs("Result"));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("successMessage")));
        this.navigateToNotesTab();

        // check if note is shown in table
        assertNotNull(this.findNoteEntry(NOTE_TITLE_EDIT, NOTE_DESCRIPTION_EDIT));
    }

    @Test
    public void deleteNote() {
        final String NOTE_TITLE = "test_title";
        final String NOTE_DESCRIPTION = "test_description";

        WebDriverWait wait = new WebDriverWait(driver, 2);

        // insert note into db
        this.noteService.insertNote(this.userId, new NotePayload("", NOTE_TITLE, NOTE_DESCRIPTION));

        // find note
        this.navigateToNotesTab();
        WebElement expectedNoteDetail = this.findNoteEntry(NOTE_TITLE, NOTE_DESCRIPTION);
        assertNotNull(expectedNoteDetail);

        // delete note
        expectedNoteDetail.findElement(By.className("delete-note-button")).click();

        // check result page
        wait.until(ExpectedConditions.titleIs("Result"));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("successMessage")));
        this.navigateToNotesTab();

        // check if note is no longer in table
        assertNull(this.findNoteEntry(NOTE_TITLE, NOTE_DESCRIPTION));
    }

    private void navigateToNotesTab() {
        WebDriverWait wait = new WebDriverWait(driver, 2);
        this.driver.get("http://localhost:" + this.port + "/");
        this.driver.findElement(By.id("nav-notes-tab")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-notes")));
    }

    private WebElement findNoteEntry(String title, String description) {
        for (WebElement noteDetail : this.driver.findElements(By.className("note-detail"))) {
            String noteTitle = noteDetail.findElement(By.className("note-title")).getText();
            String noteDescription = noteDetail.findElement(By.className("note-description")).getText();
            if (noteTitle.equals(title) && noteDescription.equals(description)) {
                return noteDetail;
            }
        }
        return null;
    }
}
