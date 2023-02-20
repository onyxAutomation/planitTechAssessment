package testCases;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import dataProviders.ConfigFileReader;
import pages.JupiterToys_CartPage;
import pages.JupiterToys_ContactPage;
import pages.JupiterToys_HomePage;
import pages.JupiterToys_ShopPage;
import utils.ExcelUtils;

public class PlanIt_CodeChallenge_JupiterToys {

	static String projectPathValue = System.getProperty("user.dir");
	ConfigFileReader configFileReader= new ConfigFileReader();
	private static WebDriver driver = null;
	ExcelUtils excelUtils = new ExcelUtils(projectPathValue+"/src/main/resources/testData/data.xlsx", "Sheet1");

	@BeforeMethod
	public void setUp() {
		
		System.setProperty("webdriver.chrome.driver", projectPathValue+configFileReader.getDriverPath());
		driver = new ChromeDriver();
		driver.get(configFileReader.getApplicationUrl());
		driver.manage().window().maximize();
	}

	@AfterMethod
	public void tearDown() {
		driver.close();
		driver.quit();
	}

	@Test (priority = 0)
	public void TC1_ContactPageMandatoryFieldsValidation() {
		/* This test script covers Test Case 1:
		From the home page go to contact page
		Click submit button
		Verify error messages
		Populate mandatory fields
		Validate errors are gone
 		*/

		JupiterToys_HomePage homePage = new JupiterToys_HomePage(driver);
		JupiterToys_ContactPage pageContact = new JupiterToys_ContactPage(driver);
		
		String contactPageUrlValue = ExcelUtils.getCellDataString(1, 3);
		String bannerError = ExcelUtils.getCellDataString(1, 4);
		String forenameError = ExcelUtils.getCellDataString(1, 5);
		String emailError = ExcelUtils.getCellDataString(1, 6);
		String messageError = ExcelUtils.getCellDataString(1, 7);

		String forenameValue = ExcelUtils.getCellDataString(1, 8);
		String emailValue = ExcelUtils.getCellDataString(1, 9);
		String messageValue = ExcelUtils.getCellDataString(1, 10);

		//Navigates to Contact page
		homePage.clickContact();
		Assert.assertEquals(homePage.checkElementIsActive("Contact"), "active");
		Assert.assertEquals(configFileReader.getApplicationUrl()+contactPageUrlValue, driver.getCurrentUrl());

		//Click Submit button
		pageContact.clickSubmit();

		//Validate banner error message along with mandatory fields error is displayed
		Assert.assertEquals(pageContact.getAlertErrorMessage(), bannerError);
		Assert.assertEquals(pageContact.getFieldErrorMessage("Forename"), forenameError);
		Assert.assertEquals(pageContact.getFieldErrorMessage("Email"), emailError);
		Assert.assertEquals(pageContact.getFieldErrorMessage("Message"), messageError);

		//Populate mandatory fields with valid values
		pageContact.setMandatoryFields(forenameValue, emailValue, messageValue);

		//Validate errors are no longer displayed in the page
		pageContact.validateErrorMessageNotDisplayed("Forename");
		pageContact.validateErrorMessageNotDisplayed("Email");
		pageContact.validateErrorMessageNotDisplayed("Message");

	}

	@Test (priority = 1)
	public void TC2_ContactPageSubmitFeedbackValidation() {
		/* This test script covers Test Case 2:
		From the home page go to contact page
		Populate mandatory fields
		Click submit button
		Validate successful submission message
 		*/

		JupiterToys_HomePage homePage = new JupiterToys_HomePage(driver);
		JupiterToys_ContactPage contactPage = new JupiterToys_ContactPage(driver);
		
		String contactPageUrlValue = ExcelUtils.getCellDataString(1, 3);
		String forenameValue = ExcelUtils.getCellDataString(2, 1);
		String emailValue = ExcelUtils.getCellDataString(2, 2);
		String messageValue = ExcelUtils.getCellDataString(2, 3);
		String successMessage = ExcelUtils.getCellDataString(2, 4)+" "+forenameValue+ExcelUtils.getCellDataString(2, 5);

		//Navigate to Contact page
		homePage.clickContact();

		//Iterates feedback submission to ensure 100% percent pass rate
		for(int i=0; i<=4; i++) {
			//Validate user is redirected to Contact Page
			Assert.assertEquals(homePage.checkElementIsActive("Contact"), "active");
			Assert.assertEquals(configFileReader.getApplicationUrl()+contactPageUrlValue, driver.getCurrentUrl());

			//Populate Mandatory fields
			contactPage.setMandatoryFields(forenameValue, emailValue, messageValue);

			//Click Submit button
			contactPage.clickSubmit();

			//Validate Success message
			Assert.assertEquals(successMessage, contactPage.validateSuccessMessage());

			contactPage.clickBack();
		}

	}
	
	@Test (priority = 2)
	public void TC3_CartPagePurchaseValidation() {
		/* This test script covers Test Case 3:
		Buy 2 Stuffed Frog, 5 Fluffy Bunny, 3 Valentine Bear
		Go to the cart page
		Verify the subtotal for each product is correct
		Verify the price for each product
		Verify that total = sum(sub totals)
 		*/
		
		JupiterToys_HomePage homePage = new JupiterToys_HomePage(driver);
		JupiterToys_ShopPage shopPage = new JupiterToys_ShopPage(driver);
		JupiterToys_CartPage cartPage = new JupiterToys_CartPage(driver);
		
		String shopPageUrlValue = ExcelUtils.getCellDataString(3, 1);
		double item1Count = ExcelUtils.getCellDataNumber(3, 2);
		String item1Val = ExcelUtils.getCellDataString(3, 3);
		double item2Count = ExcelUtils.getCellDataNumber(3, 4);
		String item2Val = ExcelUtils.getCellDataString(3, 5);
		double item3Count = ExcelUtils.getCellDataNumber(3, 6);
		String item3Val = ExcelUtils.getCellDataString(3, 7);
		String cartMsg1 = ExcelUtils.getCellDataString(3, 8);
		String cartMsg2 = ExcelUtils.getCellDataString(3, 9);
		String cartPageUrlValue = ExcelUtils.getCellDataString(3, 10);
		String [] itemArray = new String [] {item1Val,item2Val,item3Val};
		double [] qtyArray = new double [] {item1Count,item2Count,item3Count};
		double dItemCount = item1Count + item2Count + item3Count;
		int iItemCount = (int) dItemCount;
		String sItemCount = String.valueOf(iItemCount);
		
		//Navigate to Shop page
		homePage.clickShop();
		Assert.assertEquals(homePage.checkElementIsActive("Shop"), "active");
		Assert.assertEquals(configFileReader.getApplicationUrl()+shopPageUrlValue, driver.getCurrentUrl());
		
		//Order specified items
		shopPage.clickBuyItem(item1Val, item1Count);
		shopPage.clickBuyItem(item2Val, item2Count);
		shopPage.clickBuyItem(item3Val, item3Count);
		
		//Validate cart quantity value is correct based on toys ordered
		Assert.assertEquals(sItemCount, homePage.getCartItemQty());
		
		//Navigte to Cart page
		homePage.clickCart();
		Assert.assertEquals(homePage.checkElementIsActive("Cart"), "active");
		Assert.assertEquals(configFileReader.getApplicationUrl()+cartPageUrlValue, driver.getCurrentUrl());
		
		//Validate quantity value per item ordered
		Assert.assertEquals(cartMsg1+homePage.getCartItemQty()+cartMsg2, cartPage.getCartItemsCount());
		
		//Validate table shows correct prices per item ordered
		cartPage.validateCartItemPrices(itemArray, qtyArray);
		
	}
	
}
