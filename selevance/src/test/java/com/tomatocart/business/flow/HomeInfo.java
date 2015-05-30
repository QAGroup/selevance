package com.tomatocart.business.flow;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selevance.util.Util;

import com.tomatocart.page.HomePage;


public class HomeInfo extends HomePage{
	
	public Logger log = Logger.getLogger("HomePage");
	public HomeInfo(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}

	/*public Home verifyFeatureProduct() {
		// TODO Auto-generated method stub
		return new HomeInfo();
	}
	
	public Home verifyNewProduct() {
		try{
			int dispProd= homeObj.getProductImage().size();
			log.info("Total display Prodcut " + dispProd);
			for(int i =0 ; i<dispProd ; i++){
				
			}
			return new HomeInfo();
		}catch(Exception ex){
			//ex.printStackTrace();
			BaseDriver.quit();
			log.error(ex.getMessage());
			return null;
		}		
	}*/
	public void verifyFirstProduct() {
		Util.sleep(3000);
		getProductImage().get(0).click();	
	}/*
	public Home verifyRecentProduct() {
		// TODO Auto-generated method stub
		return new HomeInfo();
	}*/

	public void searchProduct(String prod) {
		try{
			getSearchTxt().clear();
			getSearchTxt().sendKeys(prod);
			getSearchBtn().click();
			log.info("After searching apple clicked button");
		}catch(Exception ex){
			ex.printStackTrace();
			//BaseDriver.quit();
			log.error(ex.getMessage());
			//return null;
		}
	}
	
	public void searchProduct() {
		try{
			getSearchTxt().clear();
			getSearchTxt().sendKeys("Apple");
			getSearchBtn().click();
			log.info("After searching apple clicked button");
		}catch(Exception ex){
			ex.printStackTrace();
			//BaseDriver.quit();
			log.error(ex.getMessage());
			//return null;
		}
	}
/*
	public Common<Home> doCommon() {
		// TODO Auto-generated method stub
		return new CommonInfo<Home>(this);
	}
*/
	public void validateAutoComplete() {
		try{
			getSearchTxt().clear();
			getSearchTxt().sendKeys("apple");
			getSearchTxt().sendKeys(" ");
			waitForElementToDisplay(driver,lbl_Autocompleter_Locator,30);
			getAutoCompleteMore().click();
		}catch(Exception ex){
			ex.printStackTrace();
			//BaseDriver.quit();
			log.error(ex.getMessage());
		}
	}

}
