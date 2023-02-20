package pages;

import java.util.ArrayList;
import java.util.Arrays;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class JupiterToys_ShopPage {

	WebDriver driver = null;

	static Float price[] = {}; 
	static ArrayList<Float> arrayList = new ArrayList<Float>(Arrays.asList(price));

	public JupiterToys_ShopPage (WebDriver driver) {
		this.driver = driver;
	}

    public ArrayList<Float> getList() {
        return JupiterToys_ShopPage.arrayList;
    }
	
	public void clickBuyItem(String item, double qty) {
		for(int i=0; i<qty; i++) {
			driver.findElement(By.xpath("//h4[.='"+item+"']/following-sibling::p/a[.='Buy']")).click();
		}
		arrayList.add(getItemPrice(item));
	}

	public float getItemPrice(String item) {
		String price = driver.findElement(By.xpath("//h4[.='"+item+"']/following-sibling::p/span[contains(@class,'price')]")).getText();
		price = price.replace("$", "");  
		float fPrice = Float.parseFloat(price);
		return fPrice;
	}
}
