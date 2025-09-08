package com.qa.opencart.pages;

import org.openqa.selenium.By;

public class CartPage {
 
	String name  = "Cart Page" ; 
	  
	By cart = By.id("cart");
	public void Cartpage() { 
		System.out.println("this is cart page");
	}
}
