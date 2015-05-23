package com.tomatocart.page;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selevance.PlusElement;


public class HomePage extends PlusElement{
	public WebDriver driver;	
	public HomePage(WebDriver driver){
		this.driver= driver;
	}
	public By txt_Search_Locator  = By.cssSelector("#keywords");
	public WebElement getSearchTxt(){
		return driver.findElement(txt_Search_Locator);
	}
	public By btn_Search_Locator = By.cssSelector("#quickSearch");
	public WebElement getSearchBtn(){
		return driver.findElement(btn_Search_Locator);
	}	
	public By lbl_Autocompleter_Locator = By.cssSelector("ul[class='autocompleter-choices useThumbnail']");
	public WebElement getAutoCompleteDiv(){
		return driver.findElement(lbl_Autocompleter_Locator);
	}
	public By lbl_Autocompleter_More_Locator = By.cssSelector("a[class='button squre medium btn']");
	public WebElement getAutoCompleteMore(){
		return driver.findElement(lbl_Autocompleter_More_Locator);
	}
	public By img_Product_image_Locator = By.cssSelector("img[class='productImage']");
	public List<WebElement> allProductImage(){
		return driver.findElements(img_Product_image_Locator);
	}
	public List<WebElement> getProductImage() {
		List<WebElement> elm = new ArrayList<WebElement>() ;
		for(WebElement single :  allProductImage()){
			elm.add(single);
		}return elm;
	}
}
