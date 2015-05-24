package org.openqa.selevance;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.fluent.Request;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
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
	
	public void dragDrop(WebDriver wd, WebElement fromElement, WebElement toElement){
		Actions builder = new Actions(wd);
		Action dragAndDrop = builder.clickAndHold(fromElement)
				   .moveToElement(toElement)
				   .release(toElement)
				   .build();			
				dragAndDrop.perform();
	}
	public void reSize(WebDriver wd, WebElement resizeable, int posX, int posY){
		Actions action = new Actions(wd);
		Action resize = action.clickAndHold(resizeable).moveByOffset(posX,posY).release().build();
		resize.perform();
	}
	public String toolTip(WebElement element){
		return element.getAttribute("title");
	}
	public String maxLength(WebElement element){
		return element.getAttribute("maxlength");
	}
	public boolean isReadOnly(WebElement element){
		String prop = element.getAttribute("readonly");
		if(prop!=null && 
				(prop.toLowerCase().contains("readonly") ||
				prop.toLowerCase().contains("true"))){
			return true;
		}else{
			return false;
		}
	}	
	public boolean isHidden(WebElement element){
		String prop = element.getAttribute("hidden");
		if(prop!=null && 
				(prop.toLowerCase().contains("hidden") ||
				prop.toLowerCase().contains("true"))){
			return true;
		}else{
			return false;
		}
	}	
	public String getElmValue(WebElement element){
		return element.getAttribute("value");		
	}
}
