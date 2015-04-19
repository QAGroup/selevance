package org.openqa.selevance;

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
}
