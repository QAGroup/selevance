package org.openqa.selevance;

import org.openqa.selenium.WebDriver;

public class PageError {
	
	public static String getError(WebDriver driver){
		String title = driver.getTitle();
		String respMsg = null;
		if(title.contains("This page can")){
			respMsg = "Page not loading.";
			driver.navigate().refresh();
		}else if(title.contains("400")){
			respMsg = "400 Bad Request";
			driver.navigate().refresh();
		}else if(title.contains("401")){
			respMsg = "401 Unauthorized";
			driver.navigate().refresh();
		}else if(title.contains("402")){
			respMsg = "402 Payment Required";
			driver.navigate().refresh();
		}else if(title.contains("403")){
			respMsg = "403 Forbidden";
			driver.navigate().refresh();
		}else if(title.contains("404")){
			respMsg = "404 Page Not found";
			driver.navigate().refresh();
		}else if(title.contains("405")){
			respMsg = "405 Method Not Allowed";
			driver.navigate().refresh();
		}else if(title.contains("406")){
			respMsg = "406 Not Acceptable";
			driver.navigate().refresh();
		}else if(title.contains("407")){
			respMsg = "407 Proxy Authentication Required";
			driver.navigate().refresh();
		}else if(title.contains("408")){
			respMsg = "408 Request Timeout";
			driver.navigate().refresh();
		}else if(title.contains("409")){
			respMsg = "409 Conflict";
			driver.navigate().refresh();
		}else if(title.contains("500")){
			respMsg = "500 Internal Server Error";
			driver.navigate().refresh();
		}else if(title.contains("501")){
			respMsg = "501 Not Implemented";
			driver.navigate().refresh();
		}else if(title.contains("502")){
			respMsg = "502 Bad Gateway";
			driver.navigate().refresh();
		}else if(title.contains("503")){
			respMsg = "503 Service Unavailable";
			driver.navigate().refresh();
		}else if(title.contains("504")){
			respMsg = "504 Gateway Timeout";
			driver.navigate().refresh();
		}else if(title.contains("511")){
			respMsg = "511  Network Authentication Required";
			driver.navigate().refresh();
		}else if(title.contains("Exception of type")){
			respMsg = "Server Error in Microsoft .NET Framework ";
			driver.navigate().refresh();
		}else{
			respMsg = "200";
		}
		return respMsg;
	}

}
