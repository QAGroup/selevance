package org.openqa.selevance.data;

import java.io.File;
import java.io.FileInputStream;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;



import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.SkipException;
import org.testng.annotations.DataProvider;

/**
 * Read Excel File for Test data
 * @author Tanmay Sarkar
 * @since 6th March 2015
 */
public class ExcelReader {
	
	/**
	 * @param String file : excel file name<br/>
	 * @param String sheet : excel sheet name	<br/>
	 * 
	 * @author Tanmay Sarkar
	 */
	@Retention(RetentionPolicy.RUNTIME)
	@Target({ElementType.METHOD})
	public @interface MSEXCEL
	{
		String file();
		String sheet();
		String format() default "BASE";
		// FIRSTYES - If only First column is YES
	}
	
	@DataProvider(name = "EXCEL", parallel = true)
	public static Object[][] XLDataProvider(Method method) {		
		MSEXCEL testData = method.getAnnotation(MSEXCEL.class);	
		String fileName =testData.file(); 
		String sheetName = testData.sheet();
		String format = testData.format();
		if(fileName.trim().length() == 0 || sheetName.trim().length() == 0){
			throw new SkipException(method.getName() + " : All Parameters are required");
		}
		if(fileName.contains(".xlsx")){
			Object[][] obj = xlsxReader(fileName,sheetName,format);
			return obj;
		}if(fileName.contains(".xls")){
			Object[][] obj = xlsReader(fileName,sheetName,format);
			return obj;
		}else{
			return null;
		}
		
	}

	public static Object[][] xlsxReader(String filename,String sheetName,String format){
		try
        {
            FileInputStream file = new FileInputStream(new File(filename));
            XSSFWorkbook workbook = new XSSFWorkbook(file);
            XSSFSheet sheet = workbook.getSheet(sheetName);
            Iterator<Row> rowIterator = sheet.iterator(); 
            Iterator<Row> rowIterator2 = sheet.iterator();                        
            int colEx1 = 0;
            ArrayList<String> rowTitle = new ArrayList<String>();
            while (rowIterator2.hasNext()){
            	Row row = rowIterator2.next();
                Iterator<Cell> cellIterator = row.cellIterator();
                while (cellIterator.hasNext())
                {
                	cellIterator.next();   
                }
            	colEx1++;
            }            
            Object[][] data = new Object[colEx1-1][1];            
            int colEx = 0;
            while (rowIterator.hasNext())
            {
                Row row = rowIterator.next();
                Iterator<Cell> cellIterator = row.cellIterator();
               	HashMap<String, String> hm = new HashMap<String, String>();
               	int i =0;
               	while (cellIterator.hasNext())
                {
                   	Cell cell = cellIterator.next();
                   	if(colEx==0){
                   		rowTitle.add(cell.toString());
                   	}else{
                   		hm.put(rowTitle.get(i), cell.toString());
                   	}
                   	i++; 
               }
               if(colEx!=0){
               		data[colEx-1][0] =hm;
               }               
               colEx ++;             
            }
            file.close();
            workbook.close();
            return data;            
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
		return null;
	}
	
	
	public static Object[][] xlsReader(String filename,String sheetName,String format){
		try
        {
            FileInputStream file = new FileInputStream(new File(filename));
            HSSFWorkbook workbook = new HSSFWorkbook(file);
            HSSFSheet sheet = workbook.getSheet(sheetName);
            Iterator<Row> rowIterator = sheet.iterator(); 
            Iterator<Row> rowIterator2 = sheet.iterator(); 
            ArrayList<ArrayList<String>> rowExl = new ArrayList<ArrayList<String>>(); 
            
            int colEx1 = 0;
            ArrayList<String> rowTitle = new ArrayList<String>();
            while (rowIterator2.hasNext()){
            	Row row = rowIterator2.next();
                Iterator<Cell> cellIterator = row.cellIterator();
                while (cellIterator.hasNext())
                {
                	cellIterator.next();   
                }
            	colEx1++;
            }            
            Object[][] data = new Object[colEx1-1][1];  
            
            int colEx = 0;
            while (rowIterator.hasNext())
            {
                Row row = rowIterator.next();
                Iterator<Cell> cellIterator = row.cellIterator();
                ArrayList<String> rowSing = new ArrayList<String>();  
                HashMap<String, String> hm = new HashMap<String, String>();
                
                if(format.trim().replace(" ", "").equalsIgnoreCase("FIRSTYES")){
                	int i = 0 ;
                	boolean isDataRow = false;
                	while (cellIterator.hasNext())
                    {
                		Cell cell = cellIterator.next();
                		// Add header
                		if(colEx==0 && i!=0){
                			isDataRow = true;
                			rowSing.add(cell.toString()); 
                		}
                		if(colEx!=0 && i==0 && 
                				cell.toString().trim().equalsIgnoreCase("YES")){
                			isDataRow = true;
                			// No need to add yes value
                			//rowSing.add(cell.toString()); 
                    	}else if(colEx!=0 && i!=0){
                    		rowSing.add(cell.toString()); 
                    	}else if(colEx!=0 ){
                    		break;
                    	}  
                        i++;
                    }
                	if(isDataRow){
                		rowExl.add(rowSing);
                	}
                }else{
                	while (cellIterator.hasNext())
                    {
                    	Cell cell = cellIterator.next();
                    	rowSing.add(cell.toString());   
                    }
                	rowExl.add(rowSing);
                }                
                colEx ++;             
            }
            file.close();
           
            Object[][] obj = {{rowExl},{rowExl}};
            return obj;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
		return null;
	}
	
}
