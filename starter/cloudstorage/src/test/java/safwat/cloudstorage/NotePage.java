package safwat.cloudstorage;




import static org.junit.jupiter.api.Assertions.assertEquals;


import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class NotePage {
	
	
	public NotePage(WebDriver driver) {
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(id = "nav-notes-tab")
	private WebElement nav_notes_tab;
	
	
	@FindBy(id = "addNewNote")
	private WebElement addNoteButton;
	
	@FindBy(id = "note-title")
	private WebElement inputNoteTitle;
	
	@FindBy(id = "note-description")
	private WebElement inputNoteDescription;
	
	@FindBy(id = "saveNoteButton")
	private WebElement saveNoteButton;
	
	@FindBy(id = "editNoteButton1")
	private WebElement editNoteButton;
	
	@FindBy(id = "deleteNoteButton")
	private WebElement deleteNoteButton;
	
	@FindBy(id = "userTable")
	private WebElement userTable;
	
	
	public void createNote(String title, String description, WebDriver driver) throws InterruptedException {
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("arguments[0].click()", nav_notes_tab);
		//this.nav_notes_tab.click();
		Thread.sleep(1000);
		this.addNoteButton.click();
		
		Thread.sleep(1000);
		
		this.inputNoteTitle.sendKeys(title);
		this.inputNoteDescription.sendKeys(description);
		
		this.saveNoteButton.click();
	}
	
	
	public void editNote(String title, String description, WebDriver driver) throws InterruptedException {
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("arguments[0].click()", nav_notes_tab);
		//this.nav_notes_tab.click();
		Thread.sleep(1000);
		this.editNoteButton.click();
		
		Thread.sleep(2000);
		
		this.inputNoteTitle.clear();
		this.inputNoteDescription.clear();
		
		
		this.inputNoteTitle.sendKeys(title);
		this.inputNoteDescription.sendKeys(description);
		
		this.saveNoteButton.click();
		
		
	}
	
	public void deleteNote(WebDriver driver) {
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("arguments[0].click()", nav_notes_tab);
		
		jse.executeScript("arguments[0].click()", this.deleteNoteButton);
		
		


	}
	
	public boolean checkNote() throws InterruptedException {
		//return !(this.userTable.findElement(By.id("1")) == null);
		
		return !(this.userTable.findElements(By.id("1")).isEmpty());
		 

	}
	
	public boolean checkNoteValues(String noteTitle, String noteDescriptio, WebDriver driver) throws InterruptedException {
		WebElement tableRow = this.userTable.findElement(By.id("1"));
		
		System.out.println("element is : " + tableRow.getTagName());
		System.out.println("element is : " + tableRow.findElement(By.id("note_th")).getTagName());
		System.out.println("element is : " + tableRow.findElement(By.id("note_td")).getTagName());

		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("arguments[0].click()", nav_notes_tab);
		
		Thread.sleep(2000);
		
		String title = tableRow.findElement(By.id("note_th")).getText();
		String description = tableRow.findElement(By.id("note_td")).getText();
		
		
		
		
		assertEquals(title, noteTitle);
		assertEquals(description, noteDescriptio);
		
		
		if( title.equals(noteTitle) && description.equals(noteDescriptio) ) {
			return true;
		}
		return false;
	}
	
	
	
	
}
