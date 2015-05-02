package org.openqa.selevance.data.reader;

import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class DBFile {
	public static Object[][] mysqlReader(String host,String username,String password,String database,String tablename){
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection connect = DriverManager.
					getConnection("jdbc:mysql://"+host+"/"+database+"?user="+username+"&password="+password);
			Statement statement = connect.createStatement();
			ResultSet resultSet = statement.executeQuery("select * from "+tablename);
			ResultSetMetaData rsmd = resultSet.getMetaData();
			resultSet.last();
			Object[][] data = new Object[resultSet.getRow()][1];
			resultSet.beforeFirst();			
			int k=0;
			while (resultSet.next()) {
				 Map<String, String> maps = new HashMap<String, String>();
				 for(int i=1;i<=rsmd.getColumnCount();i++){
					maps.put(rsmd.getColumnLabel(i), resultSet.getString(i));
				 }
				 data[k][0]= maps;
				 k++;
			 }
			 connect.close();
			 return data;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	@SuppressWarnings("unchecked")
	public static Object[][] couchReader(String host,String database){
		try {
			JSONParser parser = new JSONParser();			
			URL baseCouch = new URL(host + "/"+database+"/_all_docs");
			Reader in = new InputStreamReader(baseCouch.openStream());
			
			Object obj  = parser.parse(in);
			JSONArray a = new JSONArray();
			a.add(obj);
			
			for (Object o : a)
			  {
				  JSONObject jobj = (JSONObject) o;
				 
				  JSONArray rows = (JSONArray) jobj.get("rows");
				  int length = rows.size();
				  Object[][] data = new Object[length][1];
				  int i=0;
				  for (Object row : rows){					  
					  JSONObject jobjInside = (JSONObject)row;
					  String eachDoc = (String) jobjInside.get("id");
					  URL eachCouch = new URL(host + "/"+database+"/"+eachDoc);
					  Reader eachReader = new InputStreamReader(eachCouch.openStream());
					  Object eachObj  = parser.parse(eachReader);
					  JSONArray eachArray = new JSONArray();
					  eachArray.add(eachObj);					 
					  for (Object each : eachArray)
					  {
						  JSONObject eachJson = (JSONObject) each;	
						  System.out.println(eachJson);
						  data[i][0]=eachJson;
					  }					  
					  i++;					  
				  }
				 return data;
			  }			
			return null;
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
}
