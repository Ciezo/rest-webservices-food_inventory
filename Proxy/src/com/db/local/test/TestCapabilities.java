/*
    Document   : TestCapabilities.java
    Package	   : Proxy.src.com.db.driver;
    Created on : July 27, 2022
    Author     : Cloyd Van S. Secuya
    Description:
    	This class is responsible for testing the methods of Driver.
    	Use this class if any testing in isolated run time is needed.
*/

// PACKAGE SECTION
package com.db.local.test;


// IMPORT SECTION
import com.db.driver.Driver;
import com.db.driver.ExecQuery;

// IMPORT SECTION
import resource.User;


public class TestCapabilities {
	
	static User user; 
	
	public static void main(String[] args) {
		// TestCapabilities the capabilities of driver
		Driver driver = new Driver(); 
		
		 // Try to create a new user
		// (String user_name, String email, String password, String first_name, String last_name)
		user = new User("admin", "domain@foo.com", "secret", "Doom", "Guy"); 
		
		// Try to insert the new user on the remote PostgreSQL DB at Heroku
		driver.user_insert_newData(user);

		// Try to update an existing user record
		/**
		 * I will try to edit my user name. So, I must specify the column names 
		 * But before I do that, I must view the User table so that I can see my records
		 */
		// Queries set ups
//		ExecQuery q = new ExecQuery(); 
//		q.query("SELECT * FROM User_tbl");

//		// Upon returned results from the table I found my User_ID
//		String PK_id = "964tQHsE"; 
//		// Use the driver
//		driver.user_update_data(PK_id, "User_name", "ciezo");
//		
//		// View queries again after update
//		q.query("SELECT * FROM User_tbl");
//		
//		
//		// Fetching an existing user from the database
//		User exist_user; 
//		exist_user = driver.user_fetch_DataByID(PK_id);
//		
//		String existing_ID = exist_user.get_User_ID(); 
//		String existing_name = exist_user.getFirst_name() + " " + exist_user.getLast_name(); 
//		String existing_email = exist_user.get_UserEmail(); 
//		String existing_username = exist_user.get_UserName(); 
//		
//		System.out.println(existing_ID); 
//		System.out.println(existing_name); 
//		System.out.println(existing_email); 
//		System.out.println(existing_username); 
//		
//		
//		// Deleting a record
//		driver.user_delete_existData(PK_id);
		
	}
}
