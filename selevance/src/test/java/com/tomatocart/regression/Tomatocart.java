package com.tomatocart.regression;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;

import org.openqa.selenium.support.PageFactory;
import org.openqa.selevance.Plus;
import org.openqa.selevance.data.ActionData;
import org.openqa.selevance.data.ActionData.SelevanceAction;
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
	
		
	@SuppressWarnings({ "rawtypes"})
	@Test(description = "Sample Test case with XLSX Test data", 
			dataProviderClass= ActionData.class,
			dataProvider = "STANDARD" )
	@SelevanceAction(file = "src/test/resources/data/Action1.xlsx", 
	testcase ="TestCase",steps="Steps",index="Class",data="Data")
	public void tomatoCartBase(
			HashMap<String, String> testcase,
			ArrayList<ArrayList<String>> actions,
			HashMap<String, String> testdata) throws ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException{	
		
		System.out.println("ID : "+testcase.get("TestCaseID"));
		System.out.println("Description : "+testcase.get("Description"));
		System.out.println(testdata);
		
		Class[] hashParam = new Class[1];	
		hashParam[0] = HashMap.class;
		
		baseUrl = "http://localhost/tomatocart/";
		Plus plus = new Plus(globalConfig);
		HomeInfo homeInfo = PageFactory.initElements(plus.driver(), HomeInfo.class);
		Object homeObj = homeInfo;
		CommonInfo commonInfo = PageFactory.initElements(plus.driver(), CommonInfo.class);		
		Object commonObj = commonInfo;
		
		plus.driver().get(baseUrl);	
		
		for(ArrayList<String> inner : actions){
			String tcId = inner.get(0);
			String page = inner.get(1);
			String packageName = inner.get(2);
			String methodName = inner.get(3);
			System.out.println("Running : " + tcId + " : " +page + " : "  +packageName + " : " + methodName);
			
			switch(page.toLowerCase()){
			case "common" :
				Method commonMethod = CommonInfo.class.getDeclaredMethod(methodName, hashParam);
				commonMethod.invoke(commonObj,testdata);
				break;
			case "home" :
				Method homeMethod = HomeInfo.class.getDeclaredMethod(methodName, hashParam);
				homeMethod.invoke(homeObj,testdata);
				break;
			}
		}
		plus.quit();
	}
}
