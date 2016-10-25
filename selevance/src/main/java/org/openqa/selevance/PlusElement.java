package org.openqa.selevance;

import java.io.IOException;
import java.util.Iterator;
import java.util.Set;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.fluent.Request;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selevance.exception.PageLoadException;
import org.openqa.selevance.util.Util;

/**
 * WebElement Helper
 * @author Tanmay Sarkar
 */
public class PlusElement {

	public WebElement waitForElement(WebDriver driver,WebElement element,int time){
		WebDriverWait wait = new WebDriverWait(driver, time);
	    wait.until(ExpectedConditions.elementToBeClickable(element));
	    return element;
	}
	
	/**
	 * Only used for Lists of Web elements. Supports only for Legacy use
	 */
	@Deprecated
	public WebElement waitForElement(WebDriver driver,WebElement element){
		WebDriverWait wait = new WebDriverWait(driver, 10);
	    wait.until(ExpectedConditions.elementToBeClickable(element));
	    return element;
	}
	public boolean waitForElement(WebDriver driver,By locator){
		try{
			WebDriverWait wait = new WebDriverWait(driver, 10);
		    wait.until(ExpectedConditions.elementToBeClickable(locator));

		    return true;
		}catch(Exception ex){
			return false;
		}
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
	public WebElement waitForElementToDisplay(WebDriver driver,WebElement element,int time){
		WebDriverWait wait = new WebDriverWait(driver, time);
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
	
	public void recoveryFromConnectionLoss(WebDriver driver){
		int i;
		for(i=0;i<GlobalExtn.MAX_TRY;i++){
			String title = driver.getTitle();
			if(title.contains("404") || title.contains("This page can")){				
				driver.navigate().refresh();
			}else{break;}
		}
		if(i>0){
			//log.debug(i+" times try to recover.");
		}
	}
	
	public boolean waitForPageLoad(WebDriver driver){
		JavascriptExecutor js = (JavascriptExecutor)driver;
		int i;
		String msg = null;
		for (i=0; i<GlobalExtn.MAX_TIME_OUT; i++){ 
			Util.sleep(1000); 
			try{
				if (js.executeScript("return document.readyState").toString().equals("complete")){ 
					//System.out.println("Current ittararion : "+ i);
					//return true;		
					msg = PageError.getError(driver);
					if (msg.contains("200")) {
						System.out.println("Page laoded at " + i + " hit");
						return true;
					} else {
						// System.out.println(msg);
						if (i == GlobalExtn.MAX_TIME_OUT - 1) {
							throw new PageLoadException(GlobalExtn.MAX_TIME_OUT, msg);
						}
					}
				}else{
					//System.out.println("Check : "+js.executeScript("return document.readyState").toString());
				}   
			}catch (org.openqa.selenium.UnhandledAlertException ex) {
				System.out.println("UnhandledAlertException");
				// waitForAlertAccept(driver);
			}catch(Exception ex){
				System.out.println("Javascript Error : " + ex.getMessage());
			}
		}return false;
	}
	public boolean waitForPageLoad(WebDriver driver,int time){
		JavascriptExecutor js = (JavascriptExecutor)driver;
		int i;
		String msg = null;
		for (i=0; i<time; i++){ 
			Util.sleep(1000); 
			try{
				if (js.executeScript("return document.readyState").toString().equals("complete")){ 
					msg = PageError.getError(driver);
					System.out.println("Err: " + msg);
					if (msg.contains("200")) {
						return true;
					} else {
						//System.out.println(msg);
						if (i == GlobalExtn.MAX_TIME_OUT - 1) {
							throw new PageLoadException(GlobalExtn.MAX_TIME_OUT, msg);
						}
					}		
				}else{
					//System.out.println("Check : "+js.executeScript("return document.readyState").toString());
				}   
			}catch (org.openqa.selenium.UnhandledAlertException ex) {
				System.out.println("UnhandledAlertException");
				// waitForAlertAccept(driver);
			}catch(Exception ex){
				System.out.println("Javascript Error : " + ex.getMessage());
			}
		}return false;
	}	
	public void ieClick(WebDriver driver,WebElement w ){
		try{
			w.click();
			w.sendKeys(Keys.ENTER);
			JavascriptExecutor executor2 = (JavascriptExecutor) driver;
	        executor2.executeScript("arguments[0].click();", w);	        		
		}catch(org.openqa.selenium.UnhandledAlertException ex){
			try {
		        Alert alert = driver.switchTo().alert();
		        System.out.println("Alert data: " + alert.getText());
		        alert.accept();
		    }catch (org.openqa.selenium.NoAlertPresentException e) {
		        //System.out.println("No Alert present");
		    }catch (Exception e) {
		        e.printStackTrace();
		    }
		}catch(org.openqa.selenium.StaleElementReferenceException ex){
			//System.out.println("Info ieClick 001 : Page is navigating");
		}catch(org.openqa.selenium.ElementNotVisibleException ex){
			//System.out.println("Info ieClick 001 : Element is not displayed");
		}catch(org.openqa.selenium.InvalidElementStateException ex){
			//System.out.println("Info ieClick 001 : Element is not enabled");
		}catch(Exception e){
			System.out.println("Error ieClick 001");
			e.printStackTrace();
		}
	}
	public void ieSoftClick(WebDriver driver,WebElement w ){
		try{
			w.click();
			JavascriptExecutor executor2 = (JavascriptExecutor) driver;
	        executor2.executeScript("arguments[0].click();", w);	        		
		}catch(org.openqa.selenium.UnhandledAlertException ex){
			try {
		        Alert alert = driver.switchTo().alert();
		        System.out.println("Alert data: " + alert.getText());
		        alert.accept();
		    }catch (org.openqa.selenium.NoAlertPresentException e) {
		        //System.out.println("No Alert present");
		    }catch (Exception e) {
		        e.printStackTrace();
		    }
		}catch(org.openqa.selenium.StaleElementReferenceException ex){
			//System.out.println("Info ieClick 001 : Page is navigating");
		}catch(org.openqa.selenium.ElementNotVisibleException ex){
			//System.out.println("Info ieClick 001 : Element is not displayed");
		}catch(org.openqa.selenium.InvalidElementStateException ex){
			//System.out.println("Info ieClick 001 : Element is not enabled");
		}catch(Exception e){
			System.out.println("Error ieClick 001");
			e.printStackTrace();
		}
	}
	public void ieSoftSwitchClick(WebDriver driver,WebElement w ){
		try{
			JavascriptExecutor executor2 = (JavascriptExecutor) driver;
	        executor2.executeScript("arguments[0].click();", w);
		}catch(org.openqa.selenium.UnhandledAlertException ex){
			//System.out.println("Info ieClick 001 : Page is navigating");
		}catch(org.openqa.selenium.StaleElementReferenceException ex){
			//System.out.println("Info ieClick 002 : Page is navigating");
		}catch(org.openqa.selenium.ElementNotVisibleException ex){
			//System.out.println("Info ieClick 003 : Element is not displayed");
		}catch(org.openqa.selenium.InvalidElementStateException ex){
			//System.out.println("Info ieClick 004 : Element is not enabled");
		}catch(Exception e){
			//System.out.println("Error ieClick 005");
			e.printStackTrace();
		}
	}
	public boolean waitForAlertAccept(WebDriver driver){
		for (int i=0; i<GlobalExtn.MAX_ALERT_WAIT; i++){ 
			Util.sleep(1000); 
			try{
				Alert alert = driver.switchTo().alert();	            
	            System.out.println(alert.getText());
	            alert.accept(); 
	            break;
			}catch(Exception ex){
				//System.out.println("Alert Not Found : " + ex.getMessage());
				System.out.println("Alert Not Found : "+ i + " times");
			}
		}return false;
	}	
	public String switchTOChild(WebDriver driver) {
		String parentWindowHandler = driver.getWindowHandle(); // Store parent window
		String subWindowHandler = null;
		String outer = parentWindowHandler;
		System.out.println("Parent " + parentWindowHandler);
		Util.sleep(3000);
		Set<String> handles = driver.getWindowHandles(); // get all window handles
		Iterator<String> iterator = handles.iterator();
		while (iterator.hasNext()) {
			// subWindowHandler = iterator.next();
			outer = iterator.next();
			if (!outer.contains(parentWindowHandler)) {
				subWindowHandler = outer;
			}
			System.out.println("Inner : " + subWindowHandler);
		}
		System.out.println("Child " + subWindowHandler);
		driver.switchTo().window(subWindowHandler); // switch to popup window
		return parentWindowHandler;
	}
	
	public WebElement loadElement(WebDriver driver ,By locator) {
		int attempts = 1;
		int max = 3;
		while (attempts < max) {
			try {
				WebDriverWait wait = new WebDriverWait(driver, GlobalExtn.TIMEOUT);
				wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
				return driver.findElement(locator);
			} catch (TimeoutException te) {
				if (attempts == max) {
					return null;
				}
			} catch (NoSuchElementException nse) {
				if (attempts == max) {
					return null;
				}
			}finally{
				attempts++;
			}
		}
		return null;
	}
	public WebElement loadElement(WebDriver driver, String loc) {
		By locator = locator(loc);
		try {
			WebDriverWait wait = new WebDriverWait(driver, GlobalExtn.TIMEOUT);
			wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
			return driver.findElement(locator);
		} catch (TimeoutException te) {
			return null;
		} catch (NoSuchElementException nse) {
			return null;
		}
	}
	private By locator(String location) {
		String[] parts = location.split("::");
		String typ = parts[0];
		String locator = parts[1];
		switch (typ.toLowerCase()) {
		case "css":
			return By.cssSelector(locator);
		case "xpath":
			return By.xpath(locator);
		case "id":
			return By.id(locator);
		case "name":
			return By.name(locator);
		}
		return null;
	}
}
