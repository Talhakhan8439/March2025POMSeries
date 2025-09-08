package com.qa.opencart.tests;



import org.testng.Assert;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;

public class CartTest extends BaseTest{
   
	@Test
	public void CartpageTest() { 
		System.out.println("this is cart test page "); 
		Assert.assertTrue(true);
	}
}
