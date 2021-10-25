package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
	void registerLoginAndAddNote() {
		driver.get("http://localhost:" + port + "/signup");
		signup = new SignupPage(driver);
		signup.registerNewUser("Jon", "Doe", "jd", "123");

		driver.get("http://localhost:" + port + "/login");
		login = new LoginPage(driver);
		login.loginUser("jd", "123");

		driver.get("http://localhost:" + port + "/home");
		home = new HomePage(driver);
		home.navigateToNoteTab(driver);
		home.addNote(driver, "Shopping List", "Bread, Butter, Milk");

		driver.get("http://localhost:" + port + "/result");
		result = new ResultPage(driver);
		result.assertResultSuccessAndClickContinue(driver);

		driver.get("http://localhost:" + port + "/home");
		home = new HomePage(driver);
		home.navigateToNoteTab(driver);
		home.assertNewNoteShown(driver, "Shopping List", "Bread, Butter, Milk");
	}

}
