package safwat.cloudstorage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SignupPage {
	
	
	public SignupPage(WebDriver driver) {
		PageFactory.initElements(driver, this);
	}
	
	
	
	@FindBy(id = "inputFirstName")
	private WebElement inputFirstName;
	
	@FindBy(id = "inputLastName")
	private WebElement inputLastName;
	
	@FindBy(id = "inputUsername")
	private WebElement inputUserName;
	
	@FindBy(id = "inputPassword")
	private WebElement inputPassword;
	
	
	public void register(String[] userData) {
		this.inputFirstName.sendKeys(userData[0]);
		this.inputLastName.sendKeys(userData[1]);
		this.inputUserName.sendKeys(userData[2]);
		this.inputPassword.sendKeys(userData[3]);
		
		this.inputFirstName.submit();
	}
}
