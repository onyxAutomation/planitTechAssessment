package pages;

import java.text.DecimalFormat;
import java.time.Duration;
import java.util.ArrayList;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class JupiterToys_CartPage {

	WebDriver driver = null;

	By cartItemCountValue = By.className("cart-msg");
	By totalPrice = By.xpath("//*[contains(@class,'total')]");

	public JupiterToys_CartPage (WebDriver driver) {
		this.driver = driver;
	}
	
	public String getTotalPrice() {
		String total = driver.findElement(totalPrice).getText();
		return total;
	}

	public void validateCartItemPrices(String [] item, double [] qty) {

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//table[contains(@class,'cart-items')]/tbody/tr")));
		JupiterToys_ShopPage shopPage = new JupiterToys_ShopPage(driver);

		ArrayList<Float> arraylist = shopPage.getList();
		ArrayList<Float> subtotalList = new ArrayList<Float>();

		int count = driver.findElements(By.xpath("//table[contains(@class,'cart-items')]/tbody/tr")).size();

		for(int i=0; i<count; i++) {

			String rowData = driver.findElement(By.xpath("//table[contains(@class,'cart-items')]/tbody/tr["+(1+i)+"]")).getText();
			String rowQtyData = driver.findElement(By.xpath("//table[contains(@class,'cart-items')]/tbody/tr["+(1+i)+"]/*//*[@name='quantity']")).getAttribute("value");
			int iQty = (int) qty[i];
			String sQty = String.valueOf(iQty);
			String[] data = rowData.split("\\$");
			float fQty = Float.parseFloat(sQty);
			String actualItem = data[0].trim();
			String sActualPrice = data[1].trim();
			String itemSubtotal = data[2].trim();
			float fActualPrice = Float.parseFloat(sActualPrice);
			DecimalFormat df = new DecimalFormat("0.00");
			float fSubtotal = fQty*fActualPrice;
			float roundedFloat = (float) ((float)Math.round(fSubtotal * 100.0) / 100.0);
			String sSubtotal = df.format(fSubtotal);
			
			subtotalList.add(roundedFloat);
			
			Assert.assertEquals(rowQtyData, sQty);
			Assert.assertEquals(actualItem, item[i]);
			Assert.assertEquals(sActualPrice, arraylist.get(i).toString());
			Assert.assertEquals("$"+itemSubtotal.toString(), "$"+sSubtotal);
			
		}
		float grandTotal = 0;
		for (int i = 0; i < subtotalList.size(); i++) {  
			grandTotal += subtotalList.get(i);  
		}  
		
		Assert.assertEquals(getTotalPrice(), "Total: "+Float.toString(grandTotal));
		arraylist.clear();
	}


	public String getCartItemsCount() {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//table[contains(@class,'cart-items')]")));
		String message = driver.findElement(cartItemCountValue).getText();
		return message;
	}
}
