package com.qa.opencart.tests;

import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.qa.opencart.base.BaseTest;

public class ProductInfoTest extends BaseTest {  
	
	//BT(chrome+url) --> BC(login) --> @Test
	
	@BeforeClass  
	public void accPageSetup() { 
		accPage =loginPage.doLogin(prop.getProperty("username"), prop.getProperty("password"));
	}
	  
	
 @Test  
 public void productHeaderTest() {
	  
	 searchResultsPage = accPage.doSearch("macbook"); 
	 
	 productInfoPage = searchResultsPage.selectProduct("MacBook Pro"); 
	  
	 String actHeader = productInfoPage.getProductHeader(); 
	 
	 Assert.assertEquals(actHeader, "MacBook Pro");
 } 
 
 @DataProvider
	public Object[][] getProductImages() {
		return new Object[][] {
			{"macbook", "MacBook Pro", 4},
			{"samsung", "Samsung SyncMaster 941BW", 1},
			{"canon", "Canon EOS 5D", 3}
		};
	}
	
	
	@Test(dataProvider = "getProductImages")
	public void productImagesCountTest(String searchKey, String productName, int imageCount)  {
		searchResultsPage = accPage.doSearch(searchKey);
		productInfoPage = searchResultsPage.selectProduct(productName);
		int actImagesCount = productInfoPage.getProductImages();
		Assert.assertEquals(actImagesCount, imageCount);
	} 
	 
	@Test
	public void productInfoTest() {
		searchResultsPage = accPage.doSearch("macbook");
		productInfoPage = searchResultsPage.selectProduct("MacBook Pro");
		Map<String, String> productDataMap = productInfoPage.getProductData();
		
		SoftAssert softAssert = new SoftAssert();
		
		softAssert.assertEquals(productDataMap.get("productname"), "MacBook Pro");
		
		softAssert.assertEquals(productDataMap.get("Brand"), "Apple");
		softAssert.assertEquals(productDataMap.get("Availability"), "Out Of Stock");
		softAssert.assertEquals(productDataMap.get("Reward Points"), "800");
		softAssert.assertEquals(productDataMap.get("Product Code"), "Product 18");

		softAssert.assertEquals(productDataMap.get("productprice"), "$2,000.00");
		softAssert.assertEquals(productDataMap.get("extraprice"), "$2,000.00");

		softAssert.assertAll();//7 --> 1 failed
		
	} 
	
	//AAA pattern -- Arrange Act Assert
	// we can have multiple soft assertions in a single test case
	//but only one hard assert in the test case
	
}
