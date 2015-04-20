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



import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.SkipException;
import org.testng.annotations.DataProvider;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Read Excel File for Test data
 * @author Tanmay Sarkar
 * @since 6th March 2015
 */
public class DataReader {
	
	/**
	 * @param String file : excel file name<br/>
	 * @param String sheet : excel sheet name	<br/>
	 * 
	 * @author Tanmay Sarkar
	 */
	@Retention(RetentionPolicy.RUNTIME)
	@Target({ElementType.METHOD})
	public @interface SELEVANCEDATA
	{
		String file();
		String sheet() default "test";
		String format() default "BASE";
		// FIRSTYES - If only First column is YES
	}
	
	@DataProvider(name = "EMV", parallel = true)
	public static Object[][] XLDataProvider(Method method) {		
		SELEVANCEDATA testData = method.getAnnotation(SELEVANCEDATA.class);	
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
		}if(fileName.contains(".xml")){
			Object[][] obj = xmlReader(fileName,sheetName,format);
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
                if(format.trim().replace(" ", "").equalsIgnoreCase("FIRSTYES")){
                	boolean isDataRow = false;
                	int i=0;
                	while (cellIterator.hasNext())
                    {
                		Cell cell =cellIterator.next(); 
                    	if(i==0 && cell.toString().trim().equalsIgnoreCase("YES")){
                   			isDataRow = true;
                   			break;
                   		}
                    }
                	if(isDataRow){
                		colEx1++;
                	} 
                }else{
                	while (cellIterator.hasNext())
                    {
                    	cellIterator.next();   
                    }
                	colEx1++;
                }                
            } 
            if(format.trim().replace(" ", "").equalsIgnoreCase("FIRSTYES")){
            	colEx1++;
            }
            Object[][] data = new Object[colEx1-1][1];            
            int colEx = 0;
            int colExYes = 0;
            while (rowIterator.hasNext())
            {
                Row row = rowIterator.next();
                Iterator<Cell> cellIterator = row.cellIterator();
               	HashMap<String, String> hm = new HashMap<String, String>();
               	int i =0;
               	if(format.trim().replace(" ", "").equalsIgnoreCase("FIRSTYES")){
               		boolean isDataRow = false;
               		while (cellIterator.hasNext())
                    {
                       	Cell cell = cellIterator.next();
                       	if(colEx==0){
                       		rowTitle.add(cell.toString());
                       	}else if(colEx!=0){
                       		if(i==0 && cell.toString().trim().equalsIgnoreCase("YES")){
                       			isDataRow = true;
                       		}if(isDataRow){
                       			hm.put(rowTitle.get(i), cell.toString());
                       		}
                       		// hm.put(rowTitle.get(i), cell.toString());
                       	}
                       	i++; 
                   }
                   if(colEx!=0 && isDataRow){
                   		data[colExYes][0] =hm;
                   		colExYes++;
                   }               
                   colEx ++; 
               	}else{
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
                                  
            int colEx1 = 0;
            ArrayList<String> rowTitle = new ArrayList<String>();
            while (rowIterator2.hasNext()){
            	Row row = rowIterator2.next();
                Iterator<Cell> cellIterator = row.cellIterator();
                if(format.trim().replace(" ", "").equalsIgnoreCase("FIRSTYES")){
                	boolean isDataRow = false;
                	int i=0;
                	while (cellIterator.hasNext())
                    {
                		Cell cell =cellIterator.next(); 
                    	if(i==0 && cell.toString().trim().equalsIgnoreCase("YES")){
                   			isDataRow = true;
                   			break;
                   		}
                    }
                	if(isDataRow){
                		colEx1++;
                	} 
                }else{
                	while (cellIterator.hasNext())
                    {
                    	cellIterator.next();   
                    }
                	colEx1++;
                }                
            } 
            if(format.trim().replace(" ", "").equalsIgnoreCase("FIRSTYES")){
            	colEx1++;
            }
            Object[][] data = new Object[colEx1-1][1];            
            int colEx = 0;
            int colExYes = 0;
            while (rowIterator.hasNext())
            {
                Row row = rowIterator.next();
                Iterator<Cell> cellIterator = row.cellIterator();
               	HashMap<String, String> hm = new HashMap<String, String>();
               	int i =0;
               	if(format.trim().replace(" ", "").equalsIgnoreCase("FIRSTYES")){               		
               		boolean isDataRow = false;
               		while (cellIterator.hasNext())
                    {
                       	Cell cell = cellIterator.next();
                       	if(colEx==0){
                       		rowTitle.add(cell.toString());
                       	}else if(colEx!=0){
                       		if(i==0 && cell.toString().trim().equalsIgnoreCase("YES")){
                       			isDataRow = true;
                       		}if(isDataRow){
                       			hm.put(rowTitle.get(i), cell.toString());
                       		}
                       		// hm.put(rowTitle.get(i), cell.toString());
                       	}
                       	i++; 
                   }
                   if(colEx!=0 && isDataRow){
                   		data[colExYes][0] =hm;
                   		colExYes++;
                   }               
                   colEx ++; 
               	}else{
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
	public static Object[][] xmlReader(String filename,String sheetName,String format){
		try {

			 File stocks = new File(filename);
			 DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			 DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			 
			  
			 Document doc = dBuilder.parse(stocks);
			 doc.getDocumentElement().normalize();

			 NodeList nodes = doc.getElementsByTagName(sheetName);
			 
			 int length = nodes.getLength();
			 //System.out.println(length);
			 Object[][] data = new Object[length][1]; 
			 
			 for (int i = 0; i < nodes.getLength(); i++) 
			 {
				 Map<String, String> mMap = new HashMap<String, String>();
				 Node node = nodes.item(i);				 
				 Element element = (Element)node;				 
				 NodeList n = element.getElementsByTagNameNS("*","*");
				 
				 for(int j= 0 ; j< n.getLength() ; j++)
				 {
					 mMap.put(n.item(j).getNodeName()
							 , element.getElementsByTagName(n.item(j).getNodeName()).item(0).getTextContent());
				 }	
				 data[i][0] = mMap;
			 }
			 return data;			 
		} 
		 catch (Exception ex) {
			 ex.printStackTrace();
			 return null;
		}
	}
}
