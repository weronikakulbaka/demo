package com.example.demo;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@EnableAutoConfiguration
@ComponentScan
@SpringBootApplication
public class DemoApplication {
	public static void main(String[] args) {
            Connection connection = null;
            Statement statement = null;
		try{
			//pobranie sterownika do MySQL
                        Class.forName("com.mysql.cj.jdbc.Driver");
                        //połączenie z bazą
                        while(connection == null){
                             connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/cloud?useSSL=false&serverTimezone=UTC","wkulbaka", "wkulbaka");
                        }
                        statement = connection.createStatement();

			ResultSet resultSet = connection.getMetaData().getCatalogs();
			boolean exists = false;

			while (resultSet.next()) {
				String databaseName = resultSet.getString(1);
				if(databaseName.equals("cloud")) {
                                    exists = true;
				}
			}
			resultSet.close();
			if(!exists) {
				String sql = "CREATE DATABASE cloud";
				statement.execute(sql);
			}

			DatabaseMetaData dbm = connection.getMetaData();
			ResultSet tables = dbm.getTables(null, null, "cloud.country", null);
                        
                        if(!tableExist(connection, "country"))
			{
				String sql2 = "CREATE TABLE cloud.country (`id` int(11) NOT NULL AUTO_INCREMENT, `countryname` varchar(45) DEFAULT NULL, `capital` varchar(45) DEFAULT NULL, PRIMARY KEY (`id`)) ";
				statement.execute(sql2);
			}

		}catch(SQLException se){
			se.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
		}
                
                SpringApplication.run(DemoApplication.class, args);
		
	}
        
        
        public static boolean tableExist(Connection conn, String tableName) throws SQLException {
            boolean tExists = false;
           
            try (ResultSet rs = conn.getMetaData().getTables(null, null, tableName, null)) {
                while (rs.next()) { 
                    try{
                        String tName = rs.getString("id");
                        if (tName != null) {
                            tExists = true;
                            break;
                        }
                    }catch(SQLException se2){
                        return tExists;
                    }
                    
                }
            }
            return tExists;
        }

}
