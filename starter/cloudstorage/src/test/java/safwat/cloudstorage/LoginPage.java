package safwat.cloudstorage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {
	
	
	public LoginPage(WebDriver driver) {
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(id = "inputUsername")
	private WebElement inputUserName;
	
	@FindBy(id = "inputPassword")
	private WebElement inputPassword;
	
	
	public void login(String userName, String pass) {
		this.inputUserName.sendKeys(userName);
		this.inputPassword.sendKeys(pass);
		
		this.inputUserName.submit();
	}
}
