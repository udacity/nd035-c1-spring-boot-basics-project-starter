package com.udacity.jwdnd.course1.cloudstorage;

import static org.junit.jupiter.api.Assertions.*;

import com.udacity.jwdnd.course1.cloudstorage.pageobject.HomePage;
import com.udacity.jwdnd.course1.cloudstorage.pageobject.LoginPage;
import com.udacity.jwdnd.course1.cloudstorage.pageobject.SignupPage;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.EncryptionService;
import io.github.bonigarcia.wdm.WebDriverManager;
import java.util.function.Supplier;
import lombok.val;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CloudStorageApplicationTests {

  @Autowired private CredentialService credentialService;
  @Autowired private EncryptionService encryptionService;

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
  private final String credsUrl = "https://lols.com";
  private final String pwdChange = "fineits1234";
  private final String credsUsernameChange = "vinnytor";

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

    homePage.openNotes();
    homePage.openNoteModal();
    homePage.fillNoteTitle(noteTitle);
    homePage.fillNoteDescription(noteDesc);
    homePage.submitNoteModal();

    resultsPage.clickOnSuccessMessageLink();

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

    homePage.openNotes();
    val notesLengthBeforeEdit = homePage.getNotes().size();
    homePage.openEditModalForNote(noteTitle);
    homePage.fillNoteDescription(ninjaEdit);
    homePage.submitNoteModal();

    resultsPage.clickOnSuccessMessageLink();

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

    homePage.openNotes();
    val notesLengthBeforeEdit = homePage.getNotes().size();
    homePage.deleteNote(noteTitle);

    resultsPage.clickOnSuccessMessageLink();

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

    homePage.openCreds();
    homePage.openCredsModal();
    homePage.fillCredsUsername(credsUsername);
    homePage.fillCredsPwd(credsPwd);
    homePage.fillCredsUrl(credsUrl);
    homePage.submitCredsModal();

    resultsPage.clickOnSuccessMessageLink();

    homePage.openCreds();

    val credsFromDb = credentialService.findCredentialsByUsername(loginUsername);
    val credFromDb =
        credsFromDb.stream()
            .filter(c -> c.getUsername().equals(credsUsername) && c.getUrl().equals(credsUrl))
            .findFirst();
    val encryptionKey = credFromDb.get().getKey();

    val encryptedPwd = encryptionService.encryptValue(credsPwd, encryptionKey);

    val credsCount =
        homePage.getCreds().stream()
            .filter(
                visibleCred ->
                    credsUrl.equals(visibleCred.getUrl())
                        && credsUsername.equals(visibleCred.getUsername())
                        && encryptedPwd.equals(visibleCred.getPassword()))
            .count();

    assertEquals(1, credsCount);
  }

  @Test
  @Order(4)
  public void editCreds() {
    driver.get(domainSupplier.get() + LoginPage.urlPath);
    loginPage.loginUser(loginUsername, loginPwd);

    homePage.openCreds();
    val credsBeforeEdit = homePage.getCreds().size();
    homePage.openEditModalForCreds(credsUrl);

    val unencryptedPwd = homePage.getPwdField();

    homePage.clearCredsUsername();
    homePage.fillCredsUsername(credsUsernameChange);
    homePage.clearCredsPwd();
    homePage.fillCredsPwd(pwdChange);
    homePage.submitCredsModal();

    resultsPage.clickOnSuccessMessageLink();

    homePage.openCreds();
    val editedCreds =
        homePage.getCreds().stream()
            .filter(cred -> cred.getUrl().equals(credsUrl))
            .findFirst()
            .get();

    val newEncryptionKey =
        credentialService.findCredentialsByUsername(loginUsername).stream()
            .filter(cred -> cred.getUrl().equals(credsUrl))
            .findFirst();
    val newEncryptedPwd =
        encryptionService.encryptValue(pwdChange, newEncryptionKey.get().getKey());

    assertEquals(credsUsernameChange, editedCreds.getUsername());
    assertEquals(newEncryptedPwd, editedCreds.getPassword());
    assertEquals(credsPwd, unencryptedPwd);
    assertEquals(credsBeforeEdit, homePage.getCreds().size());
  }

  @Test
  @Order(5)
  public void deleteCreds() {
    driver.get(domainSupplier.get() + LoginPage.urlPath);
    loginPage.loginUser(loginUsername, loginPwd);

    homePage.openCreds();
    val credsLengthBeforeEdit = homePage.getCreds().size();
    homePage.deleteCreds(credsUrl);

    resultsPage.clickOnSuccessMessageLink();

    homePage.openCreds();

    val deletedCreds =
        homePage.getCreds().stream().filter(cred -> cred.getUrl().equals(credsUrl)).findFirst();
    assertFalse(deletedCreds.isPresent());
    assertEquals(homePage.getCreds().size(), credsLengthBeforeEdit - 1);
  }
}
