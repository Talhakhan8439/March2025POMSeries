package com.qa.opencart.constants;

import java.util.List;

public class AppConstants {

	
	 
	public static final int DEFAULT_SHORT_WAIT = 5 ;
	public static final int DEFAULT_MEDIUM_WAIT = 5 ;
	public static final int DEFAULT_LARGE_WAIT = 5 ; 
	
	
	public static final String LOGIN_PAGE_TITLE = "Account Login";
	public static final String LOGIN_PAGE_FRACTION_URL = "route=account/login";
	
	public static final String ACC_PAGE_TITLE = "My Account";
	public static final String ACC_PAGE_FRACTION_URL = "route=account/account"; 
	
	public static final String USER_REGISTER_SUCCESS_MESSG = "Your Account Has Been Created!";
	
	public static final int ACC_PAGE_HEADERS_COUNT = 4;
	public static List<String> expectedAccPageHeadersList = List.of("My Account", 
																	"My Orders",
																	"My Affiliate Account",
																	"Newsletter");
}
