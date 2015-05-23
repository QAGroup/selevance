package com.tomatocart.page;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selevance.PlusElement;



public class CommonPage extends PlusElement{
	public WebDriver driver;	
	public CommonPage(WebDriver driver){
		this.driver= driver;
	}
	public By lbl_price_n_sellers_Locator  = 
			By.cssSelector("div[class='boxContents']>ol>li>a");
	public List<WebElement> getPriceAndSeller() {
		return driver.findElements(lbl_price_n_sellers_Locator);
	}	
	public ArrayList<WebElement> getPriceList() {
		List<WebElement> price = getPriceAndSeller();
		ArrayList<WebElement> priceList = new ArrayList<WebElement>() ;
		for(int i = 0 ; i < 5 ; i++){
			priceList.add(price.get(i));
		}return priceList;
	}
	public List<WebElement> getBestSeller() {
		List<WebElement> seller = getPriceAndSeller();
		ArrayList<WebElement> sellerList = new ArrayList<WebElement>() ;
		for(int i = 5 ; i < seller.size() ; i++){
			sellerList.add(seller.get(i));
		}return sellerList;
	}
	public By lbl_search_result_price_Locator  = 
			By.cssSelector("td[class='productListing-data price']");
	public List<WebElement> getSearchResultPrice() {
		return driver.findElements(lbl_search_result_price_Locator);
	}
	public ArrayList<String> getSearchResultPriceList() {
		List<WebElement> price = getSearchResultPrice();
		ArrayList<String> priceList = new ArrayList<String>() ;
		for(int i = 0 ; i < price.size() ; i++){
			waitForElement(driver,price.get(i));
			priceList.add(price.get(i).getText().trim());
		}return priceList;
	}
}
