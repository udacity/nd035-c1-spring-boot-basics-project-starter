package safwat.cloudstorage;

import io.github.bonigarcia.wdm.WebDriverManager;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CloudStorageApplicationTests {

	@LocalServerPort
	private int port = 8080;

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
	
	@Test
	public void getSignupPage() {
		driver.get("http://localhost:" + this.port + "/signup");
		Assertions.assertEquals("Sign Up", driver.getTitle());
	}
	
	
	@Test
	public void getHomePageUnauthenticated() {
		driver.get("hhtp://localhost:" + this.port + "/home");
		assertNotEquals("Home", driver.getTitle());
	}
	
	@Test
	public void geHomePageAuthenticated() throws InterruptedException {
		signUp();
		
		login();
		
		assertEquals("Home", driver.getTitle());
		
		Thread.sleep(500);
		
		logout();
		
		Thread.sleep(1000);
		
		driver.get("hhtp://localhost:" + this.port + "/home");
		assertNotEquals("Home", driver.getTitle());
			
	}
	
	@Test
	public void createNote() throws InterruptedException {
		signUp();
		login();
		
		NotePage notePage = new NotePage(driver);
		notePage.createNote("note1", "i am a note", driver);
		
		boolean check = notePage.checkNote();
		assertTrue(check);
		
		notePage.checkNoteValues("note1", "i am a note", driver);
		
		Thread.sleep(3000);
		
		logout();
	}
	
	
	
	@Test
	public void editNote() throws InterruptedException {
		signUp();
		login();
		
		NotePage notePage = new NotePage(driver);
		//notePage.createNote("note1", "i am a note", driver);
		
		notePage.editNote("note edited", "i am not actually a note", driver);
		
		boolean check = notePage.checkNoteValues("note edited", "i am not actually a note", driver);

		assertTrue(check);
		
		
		
		logout();
	}
	
	@Test
	public void deleteNote() throws InterruptedException {
		signUp();
		login();
		
		NotePage notePage = new NotePage(driver);
		
		
		notePage.deleteNote(driver);
		
		boolean check = notePage.checkNote();
		assertFalse(check);
	}
	
	
	@Test
	public void createCredentials() throws InterruptedException {
		signUp();
		login();
		String url = "hallo.com";
		String username = "Safwat";
		String password = "heya";
		
		CredentialsPage credentialPage = new CredentialsPage(driver);
		
		credentialPage.createCredential(url, username, password, driver);
		
		
		boolean check = credentialPage.checkCredential();
		assertTrue(check);
		
		credentialPage.checkCredentialValues(url, username, password, driver);
		
		
		
		
	}
	
	@Test
	public void editCredentials() throws InterruptedException {
		signUp();
		login();
		
		
		String url = "udacity.com";
		String username = "Ahmed";
		String password = "heya";
		
		
		CredentialsPage credentialPage = new CredentialsPage(driver);
		
		credentialPage.editCredential(url, username, password, driver);
		
		boolean check = credentialPage.checkCredentialValues(url, username, password, driver);
		assertTrue(check);
		
		
		
	}
	
	public void deleteCredential() throws InterruptedException {
		signUp();
		login();
		
		CredentialsPage credentialPage = new CredentialsPage(driver);
		credentialPage.deleteCredential(driver);
		
		boolean check = credentialPage.checkCredential();
		assertFalse(check);

		
	}
	
	
	
	
	public void signUp() throws InterruptedException {
		driver.get("http://localhost:" + this.port + "/signup");
		Thread.sleep(500);
		SignupPage signPage = new SignupPage(driver);
		String[] userData = {"Ahmed", "Safwat", "AS", "pass"};
		
		signPage.register(userData);
	}
	
	public void login() {
		driver.get("http://localhost:" + this.port + "/login");
		LoginPage loginPage = new LoginPage(driver);
		loginPage.login("AS", "pass");
	}
	
	
	public void logout() {
		WebElement logoutButton = driver.findElement(By.id("logoutButton"));
		logoutButton.click();
	}

	

}
