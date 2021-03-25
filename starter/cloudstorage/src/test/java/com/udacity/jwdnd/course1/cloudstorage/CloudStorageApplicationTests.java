package com.udacity.jwdnd.course1.cloudstorage;

import static org.junit.jupiter.api.Assertions.*;

import com.udacity.jwdnd.course1.cloudstorage.pageobject.HomePage;
import com.udacity.jwdnd.course1.cloudstorage.pageobject.LoginPage;
import com.udacity.jwdnd.course1.cloudstorage.pageobject.SignupPage;
import com.udacity.jwdnd.course1.cloudstorage.testutils.JavascriptEvents;
import io.github.bonigarcia.wdm.WebDriverManager;
import java.util.function.Supplier;
import lombok.val;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CloudStorageApplicationTests {

  @LocalServerPort private int port;

  private WebDriver driver;

  private SignupPage signupPage;
  private LoginPage loginPage;
  private HomePage homePage;
  private ResultsPage resultsPage;
  private WebDriverWait wait;

  private final String loginUsername = "capnvinnytortilla";
  private final String loginPwd = "youwishpervert";
  private final String noteTitle = "titley title";
  private final String noteDesc = "descriptive description";
  private final String ninjaEdit = " and then some!";
  private final String credsUsername = "vtor";
  private final String credsPwd = "inyourdreams";
  private final String credsurl = "https://lols.com";

  private final Supplier<String> domainSupplier = () -> "http://localhost:" + this.port;

  @BeforeAll
  static void beforeAll() {
    WebDriverManager.chromedriver().setup();
  }

  @BeforeEach
  public void beforeEach() {
    this.driver = new ChromeDriver();
    this.wait = new WebDriverWait(driver, 10);
    signupPage = new SignupPage(driver);
    loginPage = new LoginPage(driver);
    homePage = new HomePage(driver);
    resultsPage = new ResultsPage(driver);
  }

  @AfterEach
  public void afterEach() {
    if (this.driver != null) {
      driver.quit();
    }
  }

  @Test
  @Order(1)
  public void redirectUnauthedUsersToLogin() {
    driver.get(domainSupplier.get() + LoginPage.urlPath);
    assertTrue(driver.getCurrentUrl().endsWith(LoginPage.urlPath));
    driver.get("http://localhost:" + this.port + HomePage.urlPath);
    assertTrue(driver.getCurrentUrl().endsWith(LoginPage.urlPath));
  }

  @Test
  @Order(1)
  public void successfulSignup() {
    driver.get(domainSupplier.get() + SignupPage.urlPath);
    signupPage.fillFirstName("vinny");
    signupPage.fillLastName("tortilla");
    signupPage.fillUsername(loginUsername);
    signupPage.fillPwd(loginPwd);
    signupPage.submitSignupForm();
    assertEquals(
        "You successfully signed up! Please continue to the login page.",
        signupPage.getSuccessMessage());
  }

  @Test
  @Order(2)
  public void unsuccessfulSignupUsernameTaken() {
    driver.get(domainSupplier.get() + SignupPage.urlPath);
    signupPage.fillFirstName("vinny");
    signupPage.fillLastName("tortilla");
    signupPage.fillUsername(loginUsername);
    signupPage.fillPwd(loginPwd);
    signupPage.submitSignupForm();
    assertEquals("username not available", signupPage.getErrorMessage());
  }

  @Test
  @Order(2)
  public void unsuccessfulLogin() {
    driver.get(domainSupplier.get() + LoginPage.urlPath);
    loginPage.fillUsername(loginUsername);
    loginPage.fillPwd("ITS_A_TRAAAAAAAP");
    loginPage.submitLoginForm();
    assertEquals("Invalid username or password", loginPage.getErrorMessage());
  }

  @Test
  @Order(2)
  public void successfulLogin() {
    driver.get(domainSupplier.get() + LoginPage.urlPath);
    loginPage.fillUsername(loginUsername);
    loginPage.fillPwd(loginPwd);
    loginPage.submitLoginForm();
    assertTrue(driver.getCurrentUrl().endsWith(HomePage.urlPath));
  }

  @Test
  @Order(3)
  public void createNote() {
    driver.get(domainSupplier.get() + LoginPage.urlPath);
    loginPage.loginUser(loginUsername, loginPwd);
    JavascriptEvents.waitForReadyState(driver);

    homePage.openNotes();
    homePage.openNoteModal();
    homePage.fillNoteTitle(noteTitle);
    homePage.fillNoteDescription(noteDesc);
    homePage.submitNoteModal();
    JavascriptEvents.waitForReadyState(driver);

    resultsPage.clickOnSuccessMessageLink();
    JavascriptEvents.waitForReadyState(driver);

    homePage.openNotes();

    val notes =
        homePage.getNotes().stream()
            .filter(
                visibleNote ->
                    noteTitle.equals(visibleNote.getNoteTitle())
                        && noteDesc.equals(visibleNote.getNoteDescription()))
            .count();

    assertEquals(1, notes);
  }

  @Test
  @Order(4)
  public void editNote() {
    driver.get(domainSupplier.get() + LoginPage.urlPath);
    loginPage.loginUser(loginUsername, loginPwd);
    JavascriptEvents.waitForReadyState(driver);

    homePage.openNotes();
    val notesLengthBeforeEdit = homePage.getNotes().size();
    homePage.openEditModalForNote(noteTitle);
    homePage.fillNoteDescription(ninjaEdit);
    homePage.submitNoteModal();

    resultsPage.clickOnSuccessMessageLink();
    JavascriptEvents.waitForReadyState(driver);

    homePage.openNotes();
    val editedNote =
        homePage.getNotes().stream()
            .filter(
                note -> {
                  return note.getNoteDescription().equals(noteDesc + ninjaEdit);
                })
            .findFirst();
    assertEquals(editedNote.get().getNoteTitle(), noteTitle);
    assertEquals(homePage.getNotes().size(), notesLengthBeforeEdit);
  }

  @Test
  @Order(5)
  public void deleteNote() {
    driver.get(domainSupplier.get() + LoginPage.urlPath);
    loginPage.loginUser(loginUsername, loginPwd);
    JavascriptEvents.waitForReadyState(driver);

    homePage.openNotes();
    val notesLengthBeforeEdit = homePage.getNotes().size();
    homePage.deleteNote(noteTitle);

    resultsPage.clickOnSuccessMessageLink();
    JavascriptEvents.waitForReadyState(driver);

    homePage.openNotes();

    val deletedNote =
        homePage.getNotes().stream()
            .filter(note -> note.getNoteTitle().equals(noteTitle))
            .findFirst();
    assertFalse(deletedNote.isPresent());
    assertEquals(homePage.getNotes().size(), notesLengthBeforeEdit - 1);
  }

  @Test
  @Order(3)
  public void createCreds() {
    driver.get(domainSupplier.get() + LoginPage.urlPath);
    loginPage.loginUser(loginUsername, loginPwd);
    JavascriptEvents.waitForReadyState(driver);

    homePage.openCreds();
    homePage.openCredsModal();
    homePage.fillCredsUsername(credsUsername);
    homePage.fillCredsPwd(credsPwd);
    homePage.fillCredsUrl(credsurl);
    homePage.submitCredsModal();
    JavascriptEvents.waitForReadyState(driver);

    resultsPage.clickOnSuccessMessageLink();
    JavascriptEvents.waitForReadyState(driver);

    homePage.openCreds();

    val creds =
        homePage.getCreds().stream()
            .filter(
                visibleCred ->
                    credsurl.equals(visibleCred.getUrl())
                        && credsUsername.equals(visibleCred.getUsername()))
            .count();

    assertEquals(1, creds);
  }
}
