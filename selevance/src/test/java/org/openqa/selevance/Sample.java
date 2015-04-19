package org.openqa.selevance;

import java.util.HashMap;

import org.openqa.selevance.data.DataReader;
import org.openqa.selevance.data.DataReader.SELEVANCEDATA;
import org.testng.annotations.Test;

/**
 * @author Tanmay
 *
 */
public class Sample {
	
	@Test(description = "Sample Description", 
			dataProviderClass= DataReader.class,
			dataProvider = "EXCEL" )
	@SELEVANCEDATA(file = "src/test/resources/data/data1.xlsx", 
			sheet ="Sheet2" , format ="FIRSTYES")
	public void doTest(HashMap<String, String> testdata){
		System.out.println(testdata);
	}

}
