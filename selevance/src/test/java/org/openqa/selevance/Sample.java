package org.openqa.selevance;

import java.util.HashMap;

import org.openqa.selevance.data.ExcelReader;
import org.openqa.selevance.data.ExcelReader.MSEXCEL;
import org.testng.annotations.Test;

/**
 * @author Tanmay
 *
 */
public class Sample {
	
	@Test(description = "Sample Description", 
			dataProviderClass= ExcelReader.class,
			dataProvider = "EXCEL")
	@MSEXCEL(file = "src/test/resources/data/data1.xlsx", 
			sheet ="Sheet2")
	public void doTest(HashMap<String, String> testdata){
		System.out.println(testdata);
	}

}
