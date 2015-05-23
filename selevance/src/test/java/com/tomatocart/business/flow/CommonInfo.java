package com.tomatocart.business.flow;

import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import com.tomatocart.page.CommonPage;

public class CommonInfo extends CommonPage {
	public Logger log = Logger.getLogger("CommonPage");
	public CommonInfo(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}

	
	public void verifyCatragory() {
		// TODO Auto-generated method stub		
	}

	public void verifyManufacturers() {
		// TODO Auto-generated method stub
		
	}

	public void verifyInformationSection() {		
		
	}

	public void verifyShopByPrice() {
		Logger log = Logger.getLogger("verifyShopByPrice");
		List<WebElement> allPrice = getPriceList();
		String priceValue ="";		
		/*for(WebElement ele : allPrice){			
			GuideLine.waitForElement(ele, 15);
			priceValue = ele.getText().trim();
			System.out.println(priceValue);
			ele.click();
			//GuideLine.waitFew(500);
		}*/
		String lower ="";
		String upper = "";
		for(int i =0 ; i < allPrice.size() ; i ++){
			List<WebElement> allPrice2 = getPriceList();
			for(int j = 0;j< allPrice2.size() ; j++){
				if(i==j){
					WebElement ele = allPrice2.get(j);					
					priceValue = (ele.getText());
					log.info(priceValue);	
					
					if(!priceValue.contains("+")){
						String[] parts = priceValue.split("~");
						lower = parts[0].trim().replace("$", "").replace(",", "");
						upper = parts[1].trim().replace("$", "").replace(",", "");
					}else{
						lower = upper ;
						upper = "99999999999.00";
					}					
					
					//log.info(upper + " : " + lower);
					ele.click();
					validateSearch(lower,upper);
					/*
					List<String> resultProduct =  commonObj.getSearchResultPriceList();
					for(String each : resultProduct){
						each = each.replace("$", "");
						if(Integer.parseInt(each) > Integer.parseInt(lower) &&
								Integer.parseInt(each) < Integer.parseInt(upper))
						{
							System.out.println("Lower : " + lower + " Upper : "+ upper + " Result Value is : " + upper);
						}
						else
						{
							System.out.println("Not match!!");
						}
					}*/
				}
			}			
		}		
	}

	public void verifyArticleCatagory() {
		// TODO Auto-generated method stub
	
	}

	public void verifyNewProduct() {
		// TODO Auto-generated method stub
		
	}

	public void verifyManufacturerInfo() {
		// TODO Auto-generated method stub
		
	}

	public void verifyProdNotification() {
		// TODO Auto-generated method stub
		
	}

	public void verifyTellAFriend() {
		// TODO Auto-generated method stub
		
	}

	public void verifyCurrency() {
		// TODO Auto-generated method stub
		
	}

	public void verifyPopularSearch() {
		// TODO Auto-generated method stub
		
	}

	

	
	private void validateSearch(String lower, String upper ){
		List<String> resultProduct =  getSearchResultPriceList();
		for(String each : resultProduct){
			if(each != null){
				each = each.replace("$", "").replace(",", "");
				if(Double.parseDouble(each) > Double.parseDouble(lower) &&
						Double.parseDouble(each) < Double.parseDouble(upper)){
					//log.info("Lower : " + lower + " Upper : "+ upper + " Result Value is : " + each);
				}else{
					//log.info("Not match!!");
				}
			}else{
				//log.info("No product found for Lower : " + lower + " Upper : " + upper );
			}
		}
	}

	
	public void verifyShopByPriceAppearance() {
		List<WebElement> allPrice = getPriceList();
		String priceValue ="";		
		for(WebElement ele : allPrice){	
			priceValue = ele.getText().trim();
			log.info("Shop by price appearing as : " + priceValue);
		}		
		
		//return this;
	}
}
