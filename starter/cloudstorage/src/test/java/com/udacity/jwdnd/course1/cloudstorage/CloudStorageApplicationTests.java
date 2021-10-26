package com.udacity.jwdnd.course1.cloudstorage;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CloudStorageApplicationTests {

	@LocalServerPort
	private int port;

	private static WebDriver driver;
	private SignupPage signup;
	private LoginPage login;
	private HomePage home;
	private ResultPage result;


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

	@Test
	public void getSignupPage() {
		driver.get("http://localhost:" + this.port + "/signup");
		Assertions.assertEquals("Sign Up", driver.getTitle());
	}

	@Test
	public void accessToHomeRestricted(){
		driver.get("http://localhost:" + this.port + "/home");
		Assertions.assertEquals("Login", driver.getTitle());
	}

	@Test
	public void failedLogin() {
		String username="tester1";
		String password="123pass";

		driver.get("http://localhost:" + this.port + "/login");
		login = new LoginPage(driver);
		login.loginUser(username, password);

		Assertions.assertNotEquals("Home",driver.getTitle());
	}

	@Test
	public void registerLogin() {
		registerAndLogin();
		Assertions.assertEquals("Home", driver.getTitle());
	}

	@Test
	public void logout() {
		registerAndLogin();
		home = new HomePage(driver);
		home.logout();
		Assertions.assertEquals("Login", driver.getTitle());
	}

	@Test
	void addNewNote() {
		registerAndLogin();

		addNote("Shopping List", "Bread, Butter, Milk");
		showResultSuccessAndContinue();

		driver.get("http://localhost:" + port + "/home");
		home = new HomePage(driver);
		home.navigateToNoteTab();
		home.assertNewNoteShown("Shopping List", "Bread, Butter, Milk");
	}

	private void addNote(String title, String description) {
		driver.get("http://localhost:" + port + "/home");
		home = new HomePage(driver);
		home.navigateToNoteTab();
		home.addNote(title, description);
	}

	@Test
	void editNote() {
		registerAndLogin();
		addNote("Wish List", "new computer");
		showResultSuccessAndContinue();

		driver.get("http://localhost:" + port + "/home");
		home = new HomePage(driver);
		home.navigateToNoteTab();
		home.editNote("Wish List", "One ring to rule them all");

		showResultSuccessAndContinue();

		driver.get("http://localhost:" + port + "/home");
		home = new HomePage(driver);
		home.navigateToNoteTab();
		home.assertNewNoteShown("Wish List", "One ring to rule them all");
	}

	@Test
	void deleteNote() {
		registerAndLogin();
		addNote("Todo List", "Tidy up, buy groceries");
		showResultSuccessAndContinue();

		driver.get("http://localhost:" + port + "/home");
		home = new HomePage(driver);
		home.navigateToNoteTab();
		home.deleteNote("Todo List");
		showResultSuccessAndContinue();

		driver.get("http://localhost:" + port + "/home");
		home = new HomePage(driver);
		home.navigateToNoteTab();
		home.assertNoteDeleted("Todo List");
	}

	@Test
	void addNewCredential() {
		registerAndLogin();
		addCredential("www.google.com", "TestUser", "12pass");
		home.assertNewCredentialShown("www.google.com", "TestUser", "12pass");
	}

	@Test
	void editCredential() {
		registerAndLogin();
		addCredential("www.google.com", "TestUser", "12pass");

		home.editCredential("www.yahoo.com", "Tester1", "pass34");
		showResultSuccessAndContinue();

		driver.get("http://localhost:" + port + "/home");
		home = new HomePage(driver);
		home.navigateToCredentialsTab();
		home.assertNewCredentialShown("www.yahoo.com", "Tester1", "pass34");
	}

	@Test
	void deleteCredential() {
		registerAndLogin();
		addCredential("www.twitter.com", "Tweety", "56pass");

		home.deleteCredential("www.twitter.com");
		showResultSuccessAndContinue();

		driver.get("http://localhost:" + port + "/home");
		home = new HomePage(driver);
		home.navigateToCredentialsTab();
		home.assertCredentialDeleted("www.twitter.com");
	}

	private void addCredential(String url, String username, String password) {
		driver.get("http://localhost:" + port + "/home");
		home = new HomePage(driver);
		home.navigateToCredentialsTab();
		home.addCredential(url, username, password);
		showResultSuccessAndContinue();

		driver.get("http://localhost:" + port + "/home");
		home = new HomePage(driver);
		home.navigateToCredentialsTab();
	}

	private void showResultSuccessAndContinue() {
		driver.get("http://localhost:" + port + "/result");
		result = new ResultPage(driver);
		result.assertResultSuccessAndClickContinue();
	}

	private void registerAndLogin() {
		driver.get("http://localhost:" + port + "/signup");
		signup = new SignupPage(driver);
		signup.registerNewUser("Jon", "Doe", "jd", "123");

		driver.get("http://localhost:" + port + "/login");
		login = new LoginPage(driver);
		login.loginUser("jd", "123");
	}

}
