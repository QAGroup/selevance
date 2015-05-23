package com.tomatocart.regression;

import java.util.HashMap;

import org.openqa.selenium.support.PageFactory;
import org.openqa.selevance.Plus;
import org.openqa.selevance.data.TestData;
import org.openqa.selevance.data.TestData.SelevanceBasic;
import org.openqa.selevance.util.Util;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.tomatocart.business.flow.CommonInfo;
import com.tomatocart.business.flow.HomeInfo;


public class Tomatocart {
	
	protected String baseUrl;
	protected String globalConfig;
	
	
	@BeforeMethod
	@Parameters({ "propconfig" })
	void setBrowser(@Optional String config){
		globalConfig = config;		
	}		
	
		
	@Test(description = "Sample Test case with XLS Test data", 
			dataProviderClass= TestData.class,
			dataProvider = "STANDARD" )
	@SelevanceBasic(file = "src/test/resources/data/tomatocart.xlsx", 
			sheet ="Sheet1" , format ="FIRSTYES")
	public void validateFirstProduct(HashMap<String, String> testdata){
		baseUrl = "http://localhost/tomatocart/";
		Plus plus = new Plus(globalConfig);
		//System.out.println(globalConfig);		
		HomeInfo homeObj = PageFactory.initElements(plus.driver(), HomeInfo.class);
		CommonInfo commonObj = PageFactory.initElements(plus.driver(), CommonInfo.class);		
		plus.driver().get(baseUrl);	
		homeObj.searchProduct(testdata.get("Device"));
		commonObj.verifyShopByPrice();
		homeObj.searchProduct(testdata.get("Device"));
		homeObj.verifyFirstProduct();
		Util.sleep(2000);
		commonObj.verifyShopByPriceAppearance();
		plus.quit();
	}
}
