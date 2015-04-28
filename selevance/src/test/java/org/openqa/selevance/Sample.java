package org.openqa.selevance;

import java.util.HashMap;

import org.openqa.selenium.By;
import org.openqa.selevance.data.TestData;
import org.openqa.selevance.data.TestData.SelevanceBasic;
import org.openqa.selevance.util.Util;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

/**
 * @author Tanmay Sarkar
 */
public class Sample {
	
	protected String globalConfig;
	protected String baseUrl;
	
	@BeforeMethod
	@Parameters({ "propconfig" })
	void setBrowser(@Optional String config){
		globalConfig = config;		
	}
	
	@Test(description = "Sample Test case with XML Test data", 
			dataProviderClass= TestData.class,
			dataProvider = "STANDARD" )
	@SelevanceBasic(file = "src/test/resources/data/data1.xml")
	public void doTest1(HashMap<String, String> testdata){	
		String name = testdata.get("Fname") + " " +testdata.get("Lname") ;
		baseUrl = "http://google.co.in";		
		Plus plus = new Plus(globalConfig);
		plus.driver().get(baseUrl);
		plus.driver().findElement(By.xpath(".//*[@id='lst-ib']")).clear();
		plus.driver().findElement(By.xpath(".//*[@id='lst-ib']")).sendKeys(name);
		Util.sleep(5000);
		plus.driver().quit();
	}
	
	@Test(description = "Sample Test case with XLS Test data", 
			dataProviderClass= TestData.class,
			dataProvider = "STANDARD" )
	@SelevanceBasic(file = "src/test/resources/data/data1.xls", 
			sheet ="Sheet2" , format ="FIRSTYES")
	public void doTest2(HashMap<String, String> testdata){		
		String name = testdata.get("Fname") + " " +testdata.get("Lname") ;
		baseUrl = "http://google.co.in";		
		Plus plus = new Plus(globalConfig);
		plus.driver().get(baseUrl);
		plus.driver().findElement(By.xpath(".//*[@id='lst-ib']")).clear();
		plus.driver().findElement(By.xpath(".//*[@id='lst-ib']")).sendKeys(name);
		Util.sleep(5000);
		plus.driver().quit();
	}
	
	@Test(description = "Sample Test case with XLSX Test data", 
			dataProviderClass= TestData.class,
			dataProvider = "STANDARD" )
	@SelevanceBasic(file = "src/test/resources/data/data1.xlsx", 
			sheet ="Sheet2" , format ="FIRSTYES")
	public void doTest3(HashMap<String, String> testdata){		
		String name = testdata.get("Fname") + " " +testdata.get("Lname") ;
		baseUrl = "http://google.co.in";		
		Plus plus = new Plus(globalConfig);
		plus.driver().get(baseUrl);
		plus.driver().findElement(By.xpath(".//*[@id='lst-ib']")).clear();
		plus.driver().findElement(By.xpath(".//*[@id='lst-ib']")).sendKeys(name);
		Util.sleep(5000);
		plus.driver().quit();
	}
	
	@Test(description = "Sample Test case with CSV Test data", 
			dataProviderClass= TestData.class,
			dataProvider = "STANDARD" )
	@SelevanceBasic(file = "src/test/resources/data/data1.csv")
	public void doTest4(HashMap<String, String> testdata){	
		String name = testdata.get("Fname") + " " +testdata.get("Lname") ;		
		baseUrl = "http://google.co.in";		
		Plus plus = new Plus(globalConfig);
		plus.driver().get(baseUrl);
		plus.driver().findElement(By.xpath(".//*[@id='lst-ib']")).clear();
		plus.driver().findElement(By.xpath(".//*[@id='lst-ib']")).sendKeys(name);
		Util.sleep(5000);
		plus.driver().quit();
	}
	
	@Test(description = "Sample Test case with JSON Test data",
			dataProviderClass= TestData.class,
			dataProvider = "STANDARD" )
	@SelevanceBasic(file = "src/test/resources/data/data1.json")
	public void doTest5(HashMap<String, String> testdata){	
		String name = testdata.get("Fname") + " " +testdata.get("Lname") ;
		baseUrl = "http://google.co.in";		
		Plus plus = new Plus(globalConfig);
		plus.driver().get(baseUrl);
		plus.driver().findElement(By.xpath(".//*[@id='lst-ib']")).clear();
		plus.driver().findElement(By.xpath(".//*[@id='lst-ib']")).sendKeys(name);
		Util.sleep(5000);
		plus.driver().quit();
	}

}
