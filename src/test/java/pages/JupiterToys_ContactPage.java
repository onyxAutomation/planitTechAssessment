package pages;

import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class JupiterToys_ContactPage {
	
	WebDriver driver = null;
	
	By lbl_alertInfo = By.xpath("//*[contains(@class,'alert-info')]");
	By lbl_alertError = By.xpath("//*[contains(@class,'alert-error')]");
	By lbl_alertSuccess = By.xpath("//*[contains(@class,'alert-success')]");
	
	By lbl_forename = By.xpath("//label[contains(text(),'Forename')]");
	By lbl_surname = By.xpath("//label[contains(text(),'Surname')]");
	By lbl_email = By.xpath("//label[contains(text(),'Email')]");
	By lbl_telephone = By.xpath("//label[contains(text(),'Telephone')]");
	By lbl_message = By.xpath("//label[contains(text(),'Message')]");
	
	By txt_forename = By.id("forename");
	By txt_surname = By.id("surname");
	By txt_email = By.id("email");
	By lbl_emailInfo1 = By.xpath("//*[@id='email']/following-sibling::span[1]");
	By lbl_emailInfo2 = By.xpath("//*[@id='email']/following-sibling::span[2]");
	By txt_telephone = By.id("telephone");
	By lbl_telephoneInfo = By.xpath("//*[@id='telephone']/following-sibling::span");
	By txt_message = By.id("message");
	
	By btn_submit = By.xpath("//a[contains(text(),'Submit')]");
	By btn_back = By.xpath("//a[contains(text(),'Back')]");
	
	public JupiterToys_ContactPage (WebDriver driver) {
		this.driver = driver;
	}
	
	public void clickSubmit() {
		driver.findElement(btn_submit).click();
	}
	
	public void clickBack() {
		driver.findElement(btn_back).click();
	}
	
	public void setForename (String forname) {
		driver.findElement(txt_forename).clear();
		driver.findElement(txt_forename).sendKeys(forname);
	}

	public void setEmail (String email) {
		driver.findElement(txt_email).clear();
		driver.findElement(txt_email).sendKeys(email);
	}
	
	public void setMessage (String message) {
		driver.findElement(txt_message).clear();
		driver.findElement(txt_message).sendKeys(message);
	}
	
	public void setMandatoryFields(String forename, String email, String message) {
		setForename(forename);
		setEmail(email);
		setMessage(message);
	}
	
	public String getAlertErrorMessage() {
		String errMsg = driver.findElement(lbl_alertError).getText();
		return errMsg;
	}
	
	public String getFieldErrorMessage(String field) {
		field = field.toLowerCase();
		String errMsg = driver.findElement(By.xpath("//*[@id='"+field+"-err']")).getText();
		return errMsg;
	}
	
	public void validateErrorMessageNotDisplayed(String field) {
		field = field.toLowerCase();
		int element = driver.findElements(By.xpath("//*[@id='"+field+"-err']")).size();
		Assert.assertEquals(element, 0);
	}
	
	public String validateSuccessMessage() {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
		wait.until(ExpectedConditions.presenceOfElementLocated(lbl_alertSuccess));
		String attribute = driver.findElement(lbl_alertSuccess).getText();
		return attribute;
	}

}
