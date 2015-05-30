package com.tomatocart.regression;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;

import org.openqa.selenium.support.PageFactory;
import org.openqa.selevance.Plus;
import org.openqa.selevance.data.ActionData;
import org.openqa.selevance.data.TestData;
import org.openqa.selevance.data.ActionData.SelevanceAction;
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
	
	@Test(description = "Sample Test case with XLSX Test data", 
			dataProviderClass= ActionData.class,
			dataProvider = "STANDARD" )
	@SelevanceAction(file = "src/test/resources/data/Action1.xlsx", 
	testcase ="TestCase",steps="Steps",index="Class",data="Data")
	public void tomatoCartBase(HashMap<String, String> testdata,ArrayList<ArrayList<String>> a) throws ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException{	
		
		Class<?> noparams[] = {};		
		
		baseUrl = "http://localhost/tomatocart/";
		Plus plus = new Plus(globalConfig);
		HomeInfo homeInfo = PageFactory.initElements(plus.driver(), HomeInfo.class);
		Object homeObj = homeInfo;
		CommonInfo commonInfo = PageFactory.initElements(plus.driver(), CommonInfo.class);		
		Object commonObj = commonInfo;
		
		plus.driver().get(baseUrl);	
		
		for(ArrayList<String> inner : a){
			String tcId = inner.get(0);
			String page = inner.get(1);
			String packageName = inner.get(2);
			String methodName = inner.get(3);
			System.out.println("Running : " + tcId + " : " +page + " : "  +packageName + " : " + methodName);
			
			switch(page.toLowerCase()){
			case "common" :
				Method commonMethod = CommonInfo.class.getDeclaredMethod(methodName, noparams);
				commonMethod.invoke(commonObj);
				break;
			case "home" :
				Method homeMethod = HomeInfo.class.getDeclaredMethod(methodName, noparams);
				homeMethod.invoke(homeObj);
				break;
			}
		}
		plus.quit();
	}
}
