package org.openqa.selevance;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.fluent.Request;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * @author Tanmay
 *
 */
public class PlusElement {

	public WebElement waitForElement(WebDriver driver,WebElement element,int time){
		WebDriverWait wait = new WebDriverWait(driver, time);
	    wait.until(ExpectedConditions.elementToBeClickable(element));
	    return element;
	}
	public WebElement waitForElement(WebDriver driver,WebElement element){
		WebDriverWait wait = new WebDriverWait(driver, 10);
	    wait.until(ExpectedConditions.elementToBeClickable(element));
	    return element;
	}
	
	public WebElement waitForElementToDisplay(WebDriver driver,By locator,int time){
		WebDriverWait wait = new WebDriverWait(driver, time);
	    wait.until(ExpectedConditions.presenceOfElementLocated(locator));
	    return driver.findElement(locator);
	}
	public WebElement waitForElementToDisplay(WebDriver driver,WebElement element){
		WebDriverWait wait = new WebDriverWait(driver, 10);
	    wait.until(ExpectedConditions.visibilityOf(element));
	    return element;
	}
	public int httpStatus(String url){
		int status = 0;
		try {
			status = Request.Get(url).execute().returnResponse().getStatusLine().getStatusCode();
		}catch (ClientProtocolException e) {			
			e.printStackTrace();
		}catch (IOException e) {
			e.printStackTrace();
		}return status;
	}
	public boolean urlPresent(String url){
		if(httpStatus(url)==404){
			return true;
		}else
			return false;
	}
}
