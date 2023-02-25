package com.udacity.jwdnd.course1.cloudstorage;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import java.io.File;
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CloudStorageApplicationTests {

	@LocalServerPort
	private int port;

	private WebDriver driver;

	@BeforeAll
	static void beforeAll() {
		WebDriverManager.chromedriver().setup();
	}

	@BeforeEach
	public void beforeEach() {
		this.driver = new ChromeDriver();
	}

	@AfterEach
	public void afterEach() {
		if (this.driver != null) {
			driver.quit();
		}
	}

	@Test
	public void getLoginPage() {
		driver.get("http://localhost:" + this.port + "/login");
		Assertions.assertEquals("Login", driver.getTitle());
	}

	/**
	 * PLEASE DO NOT DELETE THIS method.
	 * Helper method for Udacity-supplied sanity checks.
	 **/
	private void doMockSignUp(String firstName, String lastName, String userName, String password){
		// Create a dummy account for logging in later.

		// Visit the sign-up page.
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
		driver.get("http://localhost:" + this.port + "/signup");
		webDriverWait.until(ExpectedConditions.titleContains("Sign Up"));
		
		// Fill out credentials
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputFirstName")));
		WebElement inputFirstName = driver.findElement(By.id("inputFirstName"));
		inputFirstName.click();
		inputFirstName.sendKeys(firstName);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputLastName")));
		WebElement inputLastName = driver.findElement(By.id("inputLastName"));
		inputLastName.click();
		inputLastName.sendKeys(lastName);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputUsername")));
		WebElement inputUsername = driver.findElement(By.id("inputUsername"));
		inputUsername.click();
		inputUsername.sendKeys(userName);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputPassword")));
		WebElement inputPassword = driver.findElement(By.id("inputPassword"));
		inputPassword.click();
		inputPassword.sendKeys(password);

		// Attempt to sign up.
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("buttonSignUp")));
		WebElement buttonSignUp = driver.findElement(By.id("buttonSignUp"));
		buttonSignUp.click();

		/* Check that the sign up was successful. 
		// You may have to modify the element "success-msg" and the sign-up 
		// success message below depening on the rest of your code.
		*/
		Assertions.assertTrue(driver.findElement(By.id("success-msg")).getText().contains("You successfully signed up!"));
	}

	
	
	/**
	 * PLEASE DO NOT DELETE THIS method.
	 * Helper method for Udacity-supplied sanity checks.
	 **/
	private void doLogIn(String userName, String password)
	{
		// Log in to our dummy account.
		driver.get("http://localhost:" + this.port + "/login");
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputUsername")));
		WebElement loginUserName = driver.findElement(By.id("inputUsername"));
		loginUserName.click();
		loginUserName.sendKeys(userName);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputPassword")));
		WebElement loginPassword = driver.findElement(By.id("inputPassword"));
		loginPassword.click();
		loginPassword.sendKeys(password);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("login-button")));
		WebElement loginButton = driver.findElement(By.id("login-button"));
		loginButton.click();

		webDriverWait.until(ExpectedConditions.titleContains("Home"));
	}

	private void dologOut() {
		driver.get("http://localhost:" + this.port + "/home");
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("logOutButton")));
		WebElement uploadButton = driver.findElement(By.id("logOutButton"));
		uploadButton.click();
	}

	private void doAddNote(String noteTitle, String noteDescription) {
		driver.get("http://localhost:" + this.port + "/home");
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-notes-tab")));
		WebElement noteTabLink = driver.findElement(By.id("nav-notes-tab"));
		noteTabLink.click();

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("addNoteButton")));
		WebElement addNoteButton = driver.findElement(By.id("addNoteButton"));
		addNoteButton.click();

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("note-title")));
		WebElement noteTitleInput = driver.findElement(By.id("note-title"));
		noteTitleInput.click();
		noteTitleInput.sendKeys(noteTitle);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("note-description")));
		WebElement noteDescriptionInput = driver.findElement(By.id("note-description"));
		noteDescriptionInput.click();
		noteDescriptionInput.sendKeys(noteDescription);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("submitNoteButton")));
		WebElement submitNoteButton = driver.findElement(By.id("submitNoteButton"));
		submitNoteButton.click();
	}

	private void doEditNote(String noteTitle, String noteDescription) {
		driver.get("http://localhost:" + this.port + "/home");
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-notes-tab")));
		WebElement noteTabLink = driver.findElement(By.id("nav-notes-tab"));
		noteTabLink.click();

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("editNoteButton")));
		WebElement editNoteButton = driver.findElement(By.id("editNoteButton"));
		editNoteButton.click();

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("note-title")));
		WebElement noteTitleInput = driver.findElement(By.id("note-title"));
		noteTitleInput.clear();
		noteTitleInput.click();
		noteTitleInput.sendKeys(noteTitle);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("note-description")));
		WebElement noteDescriptionInput = driver.findElement(By.id("note-description"));
		noteDescriptionInput.clear();
		noteDescriptionInput.click();
		noteDescriptionInput.sendKeys(noteDescription);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("submitNoteButton")));
		WebElement submitNoteButton = driver.findElement(By.id("submitNoteButton"));
		submitNoteButton.click();
	}

	private void doAddCredential(String credentialUrl, String credentialUsername, String credentialPassword) {
		driver.get("http://localhost:" + this.port + "/home");
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-credentials-tab")));
		WebElement credentialTabLink = driver.findElement(By.id("nav-credentials-tab"));
		credentialTabLink.click();

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("addCredentialButton")));
		WebElement addCredentialButton = driver.findElement(By.id("addCredentialButton"));
		addCredentialButton.click();

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-url")));
		WebElement credentialUrlInput = driver.findElement(By.id("credential-url"));
		credentialUrlInput.click();
		credentialUrlInput.sendKeys(credentialUrl);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-username")));
		WebElement credentialUsernameInput = driver.findElement(By.id("credential-username"));
		credentialUsernameInput.click();
		credentialUsernameInput.sendKeys(credentialUsername);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-password")));
		WebElement credentialPasswordInput = driver.findElement(By.id("credential-password"));
		credentialPasswordInput.click();
		credentialPasswordInput.sendKeys(credentialPassword);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credentialSubmitButton")));
		WebElement credentialSubmitButton = driver.findElement(By.id("credentialSubmitButton"));
		credentialSubmitButton.click();
	}

	private void doEditCredentialNote(String credentialUrl, String credentialUsername, String credentialPassword) {
		driver.get("http://localhost:" + this.port + "/home");
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-credentials-tab")));
		WebElement credentialTabLink = driver.findElement(By.id("nav-credentials-tab"));
		credentialTabLink.click();

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("editCredentialButton")));
		WebElement editCredentialButton = driver.findElement(By.id("editCredentialButton"));
		editCredentialButton.click();

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-url")));
		WebElement credentialUrlInput = driver.findElement(By.id("credential-url"));
		credentialUrlInput.clear();
		credentialUrlInput.click();
		credentialUrlInput.sendKeys(credentialUrl);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-username")));
		WebElement credentialUsernameInput = driver.findElement(By.id("credential-username"));
		credentialUsernameInput.clear();
		credentialUsernameInput.click();
		credentialUsernameInput.sendKeys(credentialUsername);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-password")));
		WebElement credentialPasswordInput = driver.findElement(By.id("credential-password"));
		credentialPasswordInput.clear();
		credentialPasswordInput.click();
		credentialPasswordInput.sendKeys(credentialPassword);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credentialSubmitButton")));
		WebElement credentialSubmitButton = driver.findElement(By.id("credentialSubmitButton"));
		credentialSubmitButton.click();
	}

	private boolean isPlainPasswordDisplayed(String plainPassword) {
		driver.get("http://localhost:" + this.port + "/home");
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-credentials-tab")));
		WebElement credentialTabLink = driver.findElement(By.id("nav-credentials-tab"));
		credentialTabLink.click();

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("editCredentialButton")));
		WebElement editCredentialButton = driver.findElement(By.id("editCredentialButton"));
		editCredentialButton.click();

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-password")));
		WebElement credentialPasswordOutput = driver.findElement(By.id("credential-password"));

		return plainPassword.equals(credentialPasswordOutput.getAttribute("value"));
	}

	/**
	 * PLEASE DO NOT DELETE THIS TEST. You may modify this test to work with the 
	 * rest of your code. 
	 * This test is provided by Udacity to perform some basic sanity testing of 
	 * your code to ensure that it meets certain rubric criteria. 
	 * 
	 * If this test is failing, please ensure that you are handling redirecting users 
	 * back to the login page after a succesful sign up.
	 * Read more about the requirement in the rubric: 
	 * https://review.udacity.com/#!/rubrics/2724/view 
	 */
	@Test
	public void testRedirection() {
		// Create a test account
		doMockSignUp("Redirection","Test","RT","123");
		
		// Check if we have been redirected to the log in page.
		Assertions.assertEquals("http://localhost:" + this.port + "/login", driver.getCurrentUrl());
	}

	/**
	 * PLEASE DO NOT DELETE THIS TEST. You may modify this test to work with the 
	 * rest of your code. 
	 * This test is provided by Udacity to perform some basic sanity testing of 
	 * your code to ensure that it meets certain rubric criteria. 
	 * 
	 * If this test is failing, please ensure that you are handling bad URLs 
	 * gracefully, for example with a custom error page.
	 * 
	 * Read more about custom error pages at: 
	 * https://attacomsian.com/blog/spring-boot-custom-error-page#displaying-custom-error-page
	 */
	@Test
	public void testBadUrl() {
		// Create a test account
		doMockSignUp("URL","Test","UT","123");
		doLogIn("UT", "123");
		
		// Try to access a random made-up URL.
		driver.get("http://localhost:" + this.port + "/some-random-page");
		Assertions.assertFalse(driver.getPageSource().contains("Whitelabel Error Page"));
	}


	/**
	 * PLEASE DO NOT DELETE THIS TEST. You may modify this test to work with the 
	 * rest of your code. 
	 * This test is provided by Udacity to perform some basic sanity testing of 
	 * your code to ensure that it meets certain rubric criteria. 
	 * 
	 * If this test is failing, please ensure that you are handling uploading large files (>1MB),
	 * gracefully in your code. 
	 * 
	 * Read more about file size limits here: 
	 * https://spring.io/guides/gs/uploading-files/ under the "Tuning File Upload Limits" section.
	 */
	@Test
	public void testLargeUpload() {
		// Create a test account
		doMockSignUp("Large File","Test","LFT","123");
		doLogIn("LFT", "123");

		// Try to upload an arbitrary large file
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
		String fileName = "upload5m.zip";

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("fileUpload")));
		WebElement fileSelectButton = driver.findElement(By.id("fileUpload"));
		fileSelectButton.sendKeys(new File(fileName).getAbsolutePath());

		WebElement uploadButton = driver.findElement(By.id("uploadButton"));
		uploadButton.click();
		try {
			webDriverWait.until(ExpectedConditions.presenceOfElementLocated(By.id("success")));
		} catch (org.openqa.selenium.TimeoutException e) {
			System.out.println("Large File upload failed");
		}
		Assertions.assertFalse(driver.getPageSource().contains("HTTP Status 403 – Forbidden"));

	}

	@Test
	public void testUnauthorizeAccessToLoginPage() {

		driver.get("http://localhost:" + this.port + "/login");
		Assertions.assertEquals("http://localhost:" + this.port + "/login", driver.getCurrentUrl());
	}

	@Test
	public void testUnauthorizeAccessToSignUpPage() {

		driver.get("http://localhost:" + this.port + "/signup");
		Assertions.assertEquals("http://localhost:" + this.port + "/signup", driver.getCurrentUrl());
	}

	@Test
	public void testLogOut() {

		doMockSignUp("Log out","Test","Log out","123");

		doLogIn("Log out", "123");
		driver.get("http://localhost:" + this.port + "/home");
		Assertions.assertEquals("http://localhost:" + this.port + "/home", driver.getCurrentUrl());

		dologOut();
		driver.get("http://localhost:" + this.port + "/home");
		Assertions.assertEquals("http://localhost:" + this.port + "/login", driver.getCurrentUrl());
	}

	@Test
	public void testAddNoteAndVerifyNoteDisplay() {
		doMockSignUp("AddNote","Test","AddNote","123");
		doLogIn("AddNote", "123");

		String noteTitle = "Note Title";
		String noteDescription = "Note Description";
		doAddNote(noteTitle, noteDescription);

		driver.get("http://localhost:" + this.port + "/home");
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-notes-tab")));
		WebElement noteTabLink = driver.findElement(By.id("nav-notes-tab"));
		noteTabLink.click();

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("noteTitle")));
		WebElement noteTitleOutput = driver.findElement(By.id("noteTitle"));
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("noteDescription")));
		WebElement noteDescriptionOutput = driver.findElement(By.id("noteDescription"));

		Assertions.assertEquals(noteTitle, noteTitleOutput.getText());
		Assertions.assertEquals(noteDescription, noteDescriptionOutput.getText());
	}

	@Test
	public void testAddNoteThenEditNoteAndVerifyNote() {
		doMockSignUp("EditNote","Test","EditNote","123");
		doLogIn("EditNote", "123");

		String noteTitle = "Note Title";
		String noteDescription = "Note Description";
		doAddNote(noteTitle, noteDescription);

		driver.get("http://localhost:" + this.port + "/home");
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-notes-tab")));
		WebElement noteTabLink = driver.findElement(By.id("nav-notes-tab"));
		noteTabLink.click();

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("noteTitle")));
		WebElement noteTitleOutput = driver.findElement(By.id("noteTitle"));
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("noteDescription")));
		WebElement noteDescriptionOutput = driver.findElement(By.id("noteDescription"));

		Assertions.assertEquals(noteTitle, noteTitleOutput.getText());
		Assertions.assertEquals(noteDescription, noteDescriptionOutput.getText());

		String noteTitleEdited = "Note Title Edit";
		String noteDescriptionEdited = "Note Description Edit";
		doEditNote(noteTitleEdited, noteDescriptionEdited);

		webDriverWait = new WebDriverWait(driver, 2);
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-notes-tab")));
		noteTabLink = driver.findElement(By.id("nav-notes-tab"));
		noteTabLink.click();

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("noteTitle")));
		noteTitleOutput = driver.findElement(By.id("noteTitle"));
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("noteDescription")));
		noteDescriptionOutput = driver.findElement(By.id("noteDescription"));

		// to make sure that there is no new note added
		int countNoteTitle = driver.findElements(By.id("noteTitle")).size();
		int countNoteDescription = driver.findElements(By.id("noteDescription")).size();

		Assertions.assertEquals(1, countNoteTitle);
		Assertions.assertEquals(1, countNoteDescription);
		Assertions.assertEquals(noteTitleEdited, noteTitleOutput.getText());
		Assertions.assertEquals(noteDescriptionEdited, noteDescriptionOutput.getText());
	}

	@Test
	public void testAddNoteThenDeleteNoteAndVerifyNote() {
		doMockSignUp("Note","Test","Note","123");
		doLogIn("Note", "123");

		String noteTitle = "Note Title";
		String noteDescription = "Note Description";
		doAddNote(noteTitle, noteDescription);

		driver.get("http://localhost:" + this.port + "/home");
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-notes-tab")));
		WebElement noteTabLink = driver.findElement(By.id("nav-notes-tab"));
		noteTabLink.click();

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("noteTitle")));
		WebElement noteTitleOutput = driver.findElement(By.id("noteTitle"));
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("noteDescription")));
		WebElement noteDescriptionOutput = driver.findElement(By.id("noteDescription"));

		Assertions.assertEquals(noteTitle, noteTitleOutput.getText());
		Assertions.assertEquals(noteDescription, noteDescriptionOutput.getText());

		driver.get("http://localhost:" + this.port + "/home");
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-notes-tab")));
		noteTabLink = driver.findElement(By.id("nav-notes-tab"));
		noteTabLink.click();
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("deleteNoteLink")));
		WebElement deleteNoteLink = driver.findElement(By.id("deleteNoteLink"));
		deleteNoteLink.click();

		driver.get("http://localhost:" + this.port + "/home");
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-notes-tab")));
		noteTabLink = driver.findElement(By.id("nav-notes-tab"));
		noteTabLink.click();

		int countNoteTitle = driver.findElements(By.id("noteTitle")).size();
		int countNoteDescription = driver.findElements(By.id("noteDescription")).size();

		Assertions.assertEquals(0, countNoteTitle);
		Assertions.assertEquals(0, countNoteDescription);
	}

	@Test
	public void testAddCredentialAndVerifyCredentialDisplay() {
		doMockSignUp("AddCredential","Test","AddCredential","123");
		doLogIn("AddCredential", "123");

		String credentialUrl = "test-url.com";
		String credentialUsername = "test";
		String plainCredentialPassword = "password";

		doAddCredential(credentialUrl, credentialUsername, plainCredentialPassword);

		driver.get("http://localhost:" + this.port + "/home");
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-credentials-tab")));
		WebElement credentialTabLink = driver.findElement(By.id("nav-credentials-tab"));
		credentialTabLink.click();

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credentialUrl")));
		WebElement credentialUrlOutput = driver.findElement(By.id("credentialUrl"));
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credentialUsername")));
		WebElement credentialUsernameOutput = driver.findElement(By.id("credentialUsername"));
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credentialPassword")));
		WebElement credentialPasswordOutput = driver.findElement(By.id("credentialPassword"));

		Assertions.assertEquals(credentialUrl, credentialUrlOutput.getText());
		Assertions.assertEquals(credentialUsername, credentialUsernameOutput.getText());
		Assertions.assertNotEquals(plainCredentialPassword, credentialPasswordOutput.getText());
	}

	@Test
	public void testAddCredentialThenEditCredentialAndVerifyCredentialDisplay() {
		doMockSignUp("EditCredential","Test","EditCredential","123");
		doLogIn("EditCredential", "123");

		String credentialUrl = "test-url.com";
		String credentialUsername = "test";
		String plainCredentialPassword = "password";

		doAddCredential(credentialUrl, credentialUsername, plainCredentialPassword);

		driver.get("http://localhost:" + this.port + "/home");
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-credentials-tab")));
		WebElement credentialTabLink = driver.findElement(By.id("nav-credentials-tab"));
		credentialTabLink.click();

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credentialUrl")));
		WebElement credentialUrlOutput = driver.findElement(By.id("credentialUrl"));
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credentialUsername")));
		WebElement credentialUsernameOutput = driver.findElement(By.id("credentialUsername"));
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credentialPassword")));
		WebElement credentialPasswordOutput = driver.findElement(By.id("credentialPassword"));

		Assertions.assertEquals(credentialUrl, credentialUrlOutput.getText());
		Assertions.assertEquals(credentialUsername, credentialUsernameOutput.getText());
		Assertions.assertNotEquals(plainCredentialPassword, credentialPasswordOutput.getText());

		Assertions.assertTrue(isPlainPasswordDisplayed(plainCredentialPassword));

		String credentialUrlEdit = "test-url-edit.com";
		String credentialUsernameEdit = "test-edit";
		String plainCredentialPasswordEdit = "password-edit";
		doEditCredentialNote(credentialUrlEdit, credentialUsernameEdit, plainCredentialPasswordEdit);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-credentials-tab")));
		credentialTabLink = driver.findElement(By.id("nav-credentials-tab"));
		credentialTabLink.click();

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credentialUrl")));
		credentialUrlOutput = driver.findElement(By.id("credentialUrl"));
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credentialUsername")));
		credentialUsernameOutput = driver.findElement(By.id("credentialUsername"));
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credentialPassword")));
		credentialPasswordOutput = driver.findElement(By.id("credentialPassword"));

		int countCredentialUrl = driver.findElements(By.id("credentialUrl")).size();
		int countCredentialUsername = driver.findElements(By.id("credentialUsername")).size();
		int countCredentialPassword = driver.findElements(By.id("credentialPassword")).size();

		Assertions.assertEquals(1, countCredentialUrl);
		Assertions.assertEquals(1, countCredentialUsername);
		Assertions.assertEquals(1, countCredentialPassword);
		Assertions.assertEquals(credentialUrlEdit, credentialUrlOutput.getText());
		Assertions.assertEquals(credentialUsernameEdit, credentialUsernameOutput.getText());
		Assertions.assertNotEquals(plainCredentialPasswordEdit, credentialPasswordOutput.getText());
	}

	@Test
	public void testAddCredentialThenDeleteCredentialAndVerifyCredentialDisplay() {
		doMockSignUp("Credential","Test","Credential","123");
		doLogIn("Credential", "123");

		String credentialUrl = "test-url.com";
		String credentialUsername = "test";
		String plainCredentialPassword = "password";

		doAddCredential(credentialUrl, credentialUsername, plainCredentialPassword);

		driver.get("http://localhost:" + this.port + "/home");
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-credentials-tab")));
		WebElement credentialTabLink = driver.findElement(By.id("nav-credentials-tab"));
		credentialTabLink.click();

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credentialUrl")));
		WebElement credentialUrlOutput = driver.findElement(By.id("credentialUrl"));
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credentialUsername")));
		WebElement credentialUsernameOutput = driver.findElement(By.id("credentialUsername"));
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credentialPassword")));
		WebElement credentialPasswordOutput = driver.findElement(By.id("credentialPassword"));

		Assertions.assertEquals(credentialUrl, credentialUrlOutput.getText());
		Assertions.assertEquals(credentialUsername, credentialUsernameOutput.getText());
		Assertions.assertNotEquals(plainCredentialPassword, credentialPasswordOutput.getText());

		driver.get("http://localhost:" + this.port + "/home");
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-notes-tab")));
		credentialTabLink = driver.findElement(By.id("nav-credentials-tab"));
		credentialTabLink.click();
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("deleteCredentialLink")));
		WebElement deleteCredentialLink = driver.findElement(By.id("deleteCredentialLink"));
		deleteCredentialLink.click();

		driver.get("http://localhost:" + this.port + "/home");
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-notes-tab")));
		credentialTabLink = driver.findElement(By.id("nav-credentials-tab"));
		credentialTabLink.click();

		int countCredentialUrl = driver.findElements(By.id("credentialUrl")).size();
		int countCredentialUsername = driver.findElements(By.id("credentialUsername")).size();
		int countCredentialPassword = driver.findElements(By.id("credentialPassword")).size();

		Assertions.assertEquals(0, countCredentialUrl);
		Assertions.assertEquals(0, countCredentialUsername);
		Assertions.assertEquals(0, countCredentialPassword);
	}
}
