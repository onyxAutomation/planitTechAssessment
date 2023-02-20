package pages;

import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class JupiterToys_HomePage {
	
	WebDriver driver = null;
	
	By img_brand = By.className("brand");
	By menu_home = By.id("nav-home");
	By menu_shop = By.id("nav-shop");
	By menu_contact = By.id("nav-contact");
	By menu_login = By.id("nav-login");
	By menu_cart = By.id("nav-cart");
	
	public JupiterToys_HomePage (WebDriver driver) {
		this.driver = driver;
	}
	
	public String checkElementIsActive(String header) {
		header = header.toLowerCase();
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.attributeToBeNotEmpty(driver.findElement(By.xpath("//*[@id='nav-"+header+"']")), "class"));
		String attribute = driver.findElement(By.xpath("//*[@id='nav-"+header+"']")).getAttribute("class");
		return attribute;
	}
	
	public void clickBrand() {
		driver.findElement(img_brand).click();
	}
	
	public void clickHome() {
		driver.findElement(menu_home).click();
	}
	
	public void clickShop() {
		driver.findElement(menu_shop).click();
	}
	
	public void clickContact() {
		driver.findElement(menu_contact).click();
	}
	
	public void clickCart() {
		driver.findElement(menu_cart).click();
	}

	public String getCartItemQty() {
		String qty = driver.findElement(By.xpath("//*[@id='nav-cart']/*/span[contains(@class,'cart-count')]")).getText();
		return qty;
	}
}
