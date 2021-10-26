package safwat.cloudstorage;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;


public class CredentialsPage {
	
	public CredentialsPage(WebDriver driver) {
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(id = "nav-credentials-tab")
	private WebElement nav_credentials_tab;
	
	@FindBy(id = "addCredentialButton")
	private WebElement addCredentialButton;
	
	@FindBy(id = "credential-url")
	private WebElement inputCredentialUrl;
	
	@FindBy(id = "credential-username")
	private WebElement inputCredentialUsername;
	
	@FindBy(id = "credential-password")
	private WebElement inputCredentialPassword;
	
	@FindBy(id = "saveCredentialButton")
	private WebElement saveCredentialButton;
	
	@FindBy(id = "editCredentialButton")
	private WebElement editCredentialButton;
	
	@FindBy(id = "deleteCredentialButton")
	private WebElement deleteCredentialButton;
	
	@FindBy(id = "credentialTable")
	private WebElement credentialTable;
	
	public void createCredential(String url, String userName, String pass, WebDriver driver) throws InterruptedException {
		
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("arguments[0].click()", nav_credentials_tab);
		
		Thread.sleep(1000);
		this.addCredentialButton.click();
		Thread.sleep(1000);
		
		this.inputCredentialUrl.sendKeys(url);
		this.inputCredentialUsername.sendKeys(userName);
		this.inputCredentialPassword.sendKeys(pass);
		
		this.saveCredentialButton.click();
		
		WebElement homeLink = driver.findElement(By.linkText("here"));
		jse.executeScript("arguments[0].click()", homeLink);
		
		
	}
	
	public void editCredential(String url, String userName, String pass, WebDriver driver)throws InterruptedException {
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("arguments[0].click()", nav_credentials_tab);
		
		Thread.sleep(1000);
		this.editCredentialButton.click();
		Thread.sleep(1000);
		
		this.inputCredentialUrl.clear();
		this.inputCredentialUsername.clear();
		this.inputCredentialPassword.clear();
		
		this.inputCredentialUrl.sendKeys(url);
		this.inputCredentialUsername.sendKeys(userName);
		this.inputCredentialPassword.sendKeys(pass);
		
		this.saveCredentialButton.click();
		
		WebElement homeLink = driver.findElement(By.linkText("here"));
		jse.executeScript("arguments[0].click()", homeLink);
		
	}
	
	public boolean checkCredential() {
		//return !(this.credentialTable.findElement(By.id("1")) == null);
		
		return !(credentialTable.findElements(By.id("1")).isEmpty());
	}
	
	
	public void deleteCredential(WebDriver driver) {
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("arguments[0].click()", nav_credentials_tab);
		
		jse.executeScript("arguments[0].click()", this.deleteCredentialButton);
		
		
		WebElement homeLink = driver.findElement(By.linkText("here"));
		jse.executeScript("arguments[0].click()", homeLink);
		
	
	}
	
	public boolean checkCredentialValues(String credentialUrl, String credentialUserName, String credentialPass, WebDriver driver) throws InterruptedException {
		WebElement tableRow = this.credentialTable.findElement(By.id("1"));
		
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("arguments[0].click()", nav_credentials_tab);
		
		Thread.sleep(1000);
		
		
		String url = tableRow.findElement(By.id("credential_th")).getText();
		String username = tableRow.findElement(By.id("credential_td1")).getText();
		//String pass = tableRow.findElement(By.id("credential_td2")).getText();

		Thread.sleep(1000);
		this.editCredentialButton.click();
		Thread.sleep(2000);
		String pass = inputCredentialPassword.getAttribute("value");
		
		
		System.out.println("== " +  url);
		System.out.println("== " + username);
		System.out.println("== " + pass);
		
		
		if(url.equals(credentialUrl) && username.equals(credentialUserName) && pass.equals(credentialPass))
			return true;
		return false;
	}
	
	
	
}
