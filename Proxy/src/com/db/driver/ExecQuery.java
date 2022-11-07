/*
    Document   : ExecQuery.java
    Package	   : Proxy.src.com.db.driver;
    Created on : July 26, 2022
    Author     : Cloyd Van S. Secuya
    Description:
    	This is responsible for directly performing queries to the database
*/

// PACKAGE
package com.db.driver;


// IMPORT SECTION
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.db.conn.EstablishConnection;


public class ExecQuery {
	private EstablishConnection connect;
    private PreparedStatement pstatement = null;
    private ResultSet result = null;
    
    public ExecQuery() {
    	this.connect = new EstablishConnection(); 
    }
    
    public void query(String SQL) {
    	try {
    		this.pstatement = connect.getConnection().prepareStatement(SQL);
    		this.result = pstatement.executeQuery(); 
	            SQL = SQL.toLowerCase(); 
            	while(result.next()) {
            			if (SQL.contains("user_tbl")) {
            				System.out.println("=================== USER TABLE ==================");
            				System.out.println("Queries");
            				System.out.println("User ID: " + result.getString("User_ID"));
            				System.out.println("Username: " + result.getString("User_name"));
            				System.out.println("Password: " + result.getString("User_pswd"));            				
            				System.out.println("Email: " + result.getString("Email"));
            				System.out.println("First Name: " + result.getString("First_name"));
            				System.out.println("Last Name: " + result.getString("Last_name"));
            				System.out.println("=================================================");
            			}
            			
            			else if (SQL.contains("product_tbl")){
            				System.out.println("=================== PRODUCT TABLE ==================");
            				System.out.println("Product Code: " + result.getString("Product_code"));
            				System.out.println("Product Name: " + result.getString("Product_name"));
            				System.out.println("Price: " + result.getInt("Price"));
            				System.out.println("=================================================");
            			}
            			// Cart_tbl(Cart_ID, Product_code_at_cart, Product_name, Price)
            			else if (SQL.contains("cart_tbl")){
            				System.out.println("=================== CART TABLE ==================");
            				System.out.println("Cart_ID: " + result.getString("Cart_ID"));
            				System.out.println("Product_code_at_cart: " + result.getString("Product_code_at_cart"));
            				System.out.println("Product_name: " + result.getString("Product_name"));
            				System.out.println("Price: " + result.getInt("Price"));
            				System.out.println("=================================================");
            			}
            	}
    	}
    	
    	catch(SQLException e) {
    		System.out.println("An error occured!");
			e.printStackTrace();
			System.out.println(e.getMessage()); 
    	}
    }
    
}
