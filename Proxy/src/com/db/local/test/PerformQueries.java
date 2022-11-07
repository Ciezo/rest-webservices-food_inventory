/*
    Document   : PerformQueries.java
    Package	   : Proxy.src.com.db.driver;
    Created on : July 28, 2022
    Author     : Cloyd Van S. Secuya
    Description:
    	Execute the main method to start performing the SQL statement.
    	This list all the records, fields, values and attributes in the database.
*/

// PACKAGE
package com.db.local.test;


// IMPORT SECTION
import com.db.driver.ExecQuery;


public class PerformQueries {
	
	public static void main(String[] args) {
		ExecQuery q = new ExecQuery(); 
		System.out.println("\n\n");
		q.query("SELECT * FROM User_tbl");
		System.out.println("\n\n");
		q.query("SELECT * FROM Product_tbl");
		System.out.println("\n\n");
		q.query("SELECT * FROM Cart_tbl");
	}
}
