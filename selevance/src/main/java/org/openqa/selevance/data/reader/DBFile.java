package org.openqa.selevance.data.reader;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

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
}
