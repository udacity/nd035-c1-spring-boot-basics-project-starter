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

import java.nio.file.FileSystems;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CloudStorageApplicationTests {

	public String baseUrl;

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
		baseUrl = "http://localhost:" + this.port;
	}

	@AfterEach
	public void afterEach() {
		if (this.driver != null) {
			driver.quit();
		}
	}

	@Test
	@Order(1)
	public void singupAndLogin() {
		WebDriverWait wait = new WebDriverWait(driver, 10);
		driver.get(baseUrl + "/signup");
		Assertions.assertEquals("Sign Up", driver.getTitle());
		SingUp signupPage = new SingUp(driver);

		signupPage.SingUp("firstname","lastname","user1","user1");
		Assertions.assertEquals("You successfully signed up! Click here for the login page.",signupPage.getSuccessMsg(driver));

		signupPage.SingUp("firstname","lastname","user1","user1");
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("error-msg")));
		Assertions.assertEquals("Username is not available!",signupPage.getErrorMsg(driver));


		driver.get(baseUrl + "/login");
		Assertions.assertEquals("Login", driver.getTitle());
		Login login = new Login(driver);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputUsername")));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputPassword")));
		login.login("user2","user1");
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("error-msg")));
		Assertions.assertEquals("Invalid username or password",login.getErrorMsg(driver));

		login.login("user1","user1");
		Assertions.assertEquals("Home", driver.getTitle());

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("logout-button")));
		HomePage home = new HomePage(driver);
		home.logout(driver);
		Assertions.assertEquals("Login", driver.getTitle());
	}

	@Test
	@Order(2)
	public void fileUploadDownload(){
		WebDriverWait wait = new WebDriverWait(driver, 10);
		driver.get(baseUrl + "/login");
		Assertions.assertEquals("Login", driver.getTitle());
		Login login = new Login(driver);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputUsername")));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputPassword")));

		login.login("user1","user1");
		Assertions.assertEquals("Home", driver.getTitle());

		//Upload a file
		HomePage home = new HomePage(driver);
		String userDirectory = FileSystems.getDefault()
				.getPath("")
				.toAbsolutePath()
				.toString();
		home.uploadFile(userDirectory + "/src/test/java/upload.txt");

		driver.get(baseUrl + "/home");
		Assertions.assertEquals("Home", driver.getTitle());

		Assertions.assertEquals(true,home.isFilePresent("upload.txt"));
		home.downloadFile("upload.txt");
		driver.get(baseUrl + "/home");
		Assertions.assertEquals("Home", driver.getTitle());
		home.deleteFile();
		driver.get(baseUrl + "/home");
		Assertions.assertEquals("Home", driver.getTitle());
		Assertions.assertEquals(false,home.isFilePresent("upload.txt"));

	}

	@Test
	@Order(3)
	public void notesSubmitViewAndDelete(){
		WebDriverWait wait = new WebDriverWait(driver, 10);
		driver.get(baseUrl + "/login");
		Assertions.assertEquals("Login", driver.getTitle());
		Login login = new Login(driver);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputUsername")));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputPassword")));

		login.login("user1","user1");
		Assertions.assertEquals("Home", driver.getTitle());

		HomePage home = new HomePage(driver);
		home.submitNote(wait,"MyTitle","MyContent");
		driver.get(baseUrl + "/home");
		Assertions.assertEquals("Home", driver.getTitle());
		home.openNotesTab();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("note-table")));
		Assertions.assertEquals(true,home.isNotePresent("MyTitle","MyContent"));

		home.deleteNote(wait);
		driver.get(baseUrl + "/home");
		Assertions.assertEquals("Home", driver.getTitle());
		home.openNotesTab();
		Assertions.assertEquals(false,isElementPresent(By.id("note-table")));
	}

	@Test
	@Order(4)
	public void credentialsSubmitViewAndDelete(){
		WebDriverWait wait = new WebDriverWait(driver, 10);
		driver.get(baseUrl + "/login");
		Assertions.assertEquals("Login", driver.getTitle());
		Login login = new Login(driver);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputUsername")));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputPassword")));

		login.login("user1","user1");
		Assertions.assertEquals("Home", driver.getTitle());

		HomePage home = new HomePage(driver);
		home.submitCredential(wait,"myURL","myUsername","myPassword");
		driver.get(baseUrl + "/home");
		Assertions.assertEquals("Home", driver.getTitle());
		home.openCredentialsTab();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("creds-table")));
		Assertions.assertEquals(true,home.isCredentialPresent("myURL","myUsername","myPassword"));

		home.deleteCredential(wait);
		driver.get(baseUrl + "/home");
		Assertions.assertEquals("Home", driver.getTitle());
		home.openCredentialsTab();
		Assertions.assertEquals(false,isElementPresent(By.id("creds-table")));



	}

	public boolean isElementPresent(By locatorKey) {
		try {
			driver.findElement(locatorKey);
			return true;
		} catch (org.openqa.selenium.NoSuchElementException e) {
			return false;
		}
	}
}
