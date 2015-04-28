package org.openqa.selevance.data;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Method;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selevance.data.reader.BasicFile;
import org.testng.SkipException;
import org.testng.annotations.DataProvider;


/**
 * Read XLSX/XLS/XML/CSV/JSON File for Test data
 * @author Tanmay Sarkar
 * @since 6th March 2015
 */
public class TestData {
	
	/**
	 * @param String file : Test Data file name (*.xls / *.xlsx / *.csv / *.json) <br/>
	 * @param String sheet : For excel sheet name , use node name for XML	<br/>
	 * 
	 * @author Tanmay Sarkar
	 */
	@Retention(RetentionPolicy.RUNTIME)
	@Target({ElementType.METHOD})
	public @interface SelevanceBasic
	{
		String file();
		String sheet() default "test";
		String format() default "BASE";
		// FIRSTYES - If only First column is YES
	}
	
	@DataProvider(name = "STANDARD", parallel = true)
	public static Object[][] BaseDataProvider(Method method) {		
		SelevanceBasic testData = method.getAnnotation(SelevanceBasic.class);	
		String fileName =testData.file().toLowerCase(); 
		String sheetName = testData.sheet();
		String format = testData.format();
		if(fileName.trim().length() == 0 || sheetName.trim().length() == 0){
			throw new SkipException(method.getName() + " : All Parameters are required");
		}
		Matcher matcher;
	    Pattern pattern;
	    
		String regexXLSX ="(.*).xlsx$";		
	    pattern = Pattern.compile(regexXLSX);
	    matcher = pattern.matcher(fileName);	       
		if(matcher.matches()){
			Object[][] obj = BasicFile.xlsxReader(fileName,sheetName,format);
			return obj;
		}
		
		String regexXLS ="(.*).xls$";		
	    pattern = Pattern.compile(regexXLS);
	    matcher = pattern.matcher(fileName);
		if(matcher.matches()){
			Object[][] obj = BasicFile.xlsReader(fileName,sheetName,format);
			return obj;
		}
		
		String regexXML ="(.*).xml$";		
	    pattern = Pattern.compile(regexXML);
	    matcher = pattern.matcher(fileName);
		if(matcher.matches()){
			Object[][] obj = BasicFile.xmlReader(fileName,sheetName,format);
			return obj;
		}
		
		String regexCSV ="(.*).csv$";		
	    pattern = Pattern.compile(regexCSV);
	    matcher = pattern.matcher(fileName);
		if(matcher.matches()){
			Object[][] obj = BasicFile.csvReader(fileName,sheetName,format);
			return obj;
		}
		
		String regexJSON ="(.*).json$";		
	    pattern = Pattern.compile(regexJSON);
	    matcher = pattern.matcher(fileName);
		if(matcher.matches()){
			Object[][] obj = BasicFile.jsonReader(fileName,sheetName,format);
			return obj;
		}else{
			return null;
		}
		
	}

	
}
