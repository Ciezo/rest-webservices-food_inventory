/*
    Document   : Driver.java
    Package	   : Proxy.src.com.db.driver;
    Created on : July 26, 2022
    Author     : Cloyd Van S. Secuya
    Description:
    	This is the Driver that establishes communication with the Client and Server.
    	Hence, the architecture is as follows:
    	Client <---> Driver <---> Server <---> Remote Database at Heroku
    	
    	Moreover, the Driver is responsible for handling data logic because without this we 
    	cannot preview and update any changes from our remote databases hosted on Heroku.
*/

// PACKAGE SECTION
package com.db.driver;


// IMPORT SECTION
import java.sql.*;
import com.db.conn.EstablishConnection;

import resource.Cart;
import resource.Product;
import resource.User;
import java.util.ArrayList;


public class Driver {
	
	private EstablishConnection connect;
    private Statement statement = null;
    private ResultSet result = null;
	
    public Driver() {
    	/**
    	 * @default Upon initialization using the default constructor the Driver immediately performs a handshake with the server using EstablishConnection
    	 * @note Driver immediately performs a handshake with the server using EstablishConnection
    	 */
    	this.connect = new EstablishConnection(); 
    }
    
	
    /**
     * Methods responsible for User_tbl
     */
    // ==================================== BEGIN METHODS FOR USER TABLE ============================== // 
    
    // This method will insert User new data to the remote database
    public void user_insert_newData(User user) {
    	/**
    	 * @param Pass the user object to be inserted as a new record into the remote database
    	 * @note This method is responsible for recording new signed up users who just created their account
    	 */
    	
    	// Get an OK connection 
    	connect.getConnection(); 
    	
    	// SQL statement
    	String SQL = "INSERT INTO User_tbl(User_ID, User_name, Email, User_pswd, First_name, Last_name) VALUES('"+user.get_User_ID()+"','"+user.get_UserName()+"','"+user.get_UserEmail()+"','"+user.get_UserPassword()+"','"+user.getFirst_name()+"','"+user.getLast_name()+"')";
    	System.out.println("INSERT STATEMENT: " + SQL);
    	
    	try {
    		statement = connect.getConnection().createStatement();
            statement.executeUpdate(SQL);
            statement.close();
    	}
    	
    	catch(SQLException e) {
    		System.out.println("An error occured!");
			e.printStackTrace();
			System.out.println(e.getMessage()); 
    	}
    }
    
    // This method updates an existing user record
    public void user_update_data(String PK_id, String col, String val_update) {
    	/**
    	 * @param PK_id pass the PK User_ID to set the WHERE clause
    	 * @param col select the column to update from the User_tbl
    	 * @param val_update insert and update the new value
    	 */
    	// Get an OK connection 
    	connect.getConnection(); 
    	
    	// SQL Statement
    	String SQL = "UPDATE User_tbl"
    				+ " SET " + col + " = " + "'" + val_update + "'"
    				+ " WHERE User_ID = " + "'" + PK_id + "'";
    	
    	System.out.println("UPDATE STATEMENT: " + SQL);
    	
    	try {
    		statement = connect.getConnection().createStatement();
            statement.executeUpdate(SQL);
            statement.close();
    	}
    	
    	catch (SQLException e) {
    		System.out.println("An error occured!");
			e.printStackTrace();
			System.out.println(e.getMessage()); 
    	}
    }
    
    // This method will delete an existing user from the remote database by using a User_id set as PK
    public void user_delete_existData(String PK_id) {
    	/**
    	 * @param PK_id pass the value of a user_id here to be deleted from the records
    	 * @note This deletes an entire row of User data
    	 */
    	
    	// Get an OK connection 
    	connect.getConnection(); 
    	
    	// SQL Statement
    	String SQL = "DELETE FROM User_tbl WHERE User_ID = '"+PK_id+"'"; 
    	System.out.println("DELETE STATEMENT: " + SQL);
    	
    	try {
    		statement = connect.getConnection().createStatement();	
            statement.executeUpdate(SQL);
            statement.close();
    	}
    	
    	catch(SQLException e) {
    		System.out.println("An error occured!");
			e.printStackTrace();
			System.out.println(e.getMessage()); 
    	}
    }
     
    // This method will fetch and return a particular User data using an existing ID from the remote database
    public User user_fetch_DataByID(String user_id) {
    	/**
    	 * @param Pass a specific user_ID to be fetched from the remote database
    	 * @note This method returns a single User object
    	 * @return This returns a single instance of a User object
    	 */
    	
    	// Get an OK connection 
    	connect.getConnection(); 
    	
    	// SQL Statement
    	String SQL = "SELECT * FROM User_tbl WHERE User_ID = '"+user_id+"'"; 
    	System.out.println("SELECT STATEMENT: " + SQL);
    	
    	// Create an empty user object to be instantiated as it needs to be returned by this method
    	User fetch_user = new User();
    	
    	// Attributes to be assigned
    	String existing_user_id; 
    	String existing_username; 
    	String existing_email; 
    	String existing_pswd; 
    	String existing_first_name;
    	String existing_last_name;
    	
    	try {
    		statement = connect.getConnection().createStatement();
    		result = statement.executeQuery(SQL); 
    		
    			// Loop through all rows
    			while(result.next()) {
    				System.out.println("\n\nFinding user...ID -> " + user_id + " at User_tbl");
    				existing_user_id = result.getString("User_ID");
    				existing_username = result.getString("User_name"); 
    		    	existing_email = result.getString("Email"); 
    		    	existing_pswd = result.getString("User_pswd"); 
    		    	existing_first_name = result.getString("First_name");
    		    	existing_last_name = result.getString("Last_name");
    		    	
    		    	// Print to terminal for being verbose
    		    	System.out.println("Selected ID: " + existing_user_id);
    		    	System.out.println("User Full name : " + existing_first_name + " " + existing_last_name);
                    System.out.println("Fetched Username: " + existing_username);
                    System.out.println("Fetched Email: " + existing_email);
                    System.out.println("Fetched Password: " + existing_pswd);                    
                    System.out.println("Fetched First Name: " + existing_first_name);
                    System.out.println("Fetched Last Name: " + existing_last_name);
    		    	
    		    	// Assigned fetch fields to user object attributes 
    		    	fetch_user.set_User_ID(existing_user_id);
    		    	fetch_user.set_UserName(existing_username);
    		    	fetch_user.set_UserEmail(existing_email);
    		    	fetch_user.set_UserPassword(existing_pswd);
    		    	fetch_user.setFirst_name(existing_first_name);
    		    	fetch_user.setLast_name(existing_last_name);
    		    	// Return
    		    	System.out.println("Return\n\n");
    		    	return fetch_user;
    			}
    	}
    	
    	catch(SQLException e) {
    		System.out.println("An error occured!");
			e.printStackTrace();
			System.out.println(e.getMessage()); 
    	}
    	
    	return fetch_user; 
    }
    
    // This method returns a user object using an email as a constraint
    public User user_fetch_byEmail(String email) {
    	/**
    	 * @param Pass a specific emaiil to be fetched from the remote database
    	 * @note This method returns a single User object
    	 * @return This returns a single instance of a User object
    	 */
    	
    	// Get an OK connection 
    	connect.getConnection(); 
    	
    	// SQL Statement
    	String SQL = "SELECT * FROM User_tbl WHERE Email = '"+email+"'"; 
    	System.out.println("SELECT STATEMENT: " + SQL);
    	
    	// Create an empty user object to be instantiated as it needs to be returned by this method
    	User fetch_user = new User();
    	
    	// Attributes to be assigned
    	String existing_user_id; 
    	String existing_username; 
    	String existing_email; 
    	String existing_pswd;
    	String existing_first_name;
    	String existing_last_name;
    	
    	try {
    		statement = connect.getConnection().createStatement();
    		result = statement.executeQuery(SQL); 
    		
    			// Loop through all rows
    			while(result.next()) {
    				System.out.println("\n\nFinding user...Email -> " + email + " at User_tbl");
    				existing_user_id = result.getString("User_ID");
    				existing_username = result.getString("User_name"); 
    		    	existing_email = result.getString("Email"); 
    		    	existing_pswd = result.getString("User_pswd"); 
    		    	existing_first_name = result.getString("First_name");
    		    	existing_last_name = result.getString("Last_name");
    		    	
    		    	// Print to terminal for being verbose
    		    	System.out.println("Selected ID: " + existing_user_id);
    		    	System.out.println("User Full name : " + existing_first_name + " " + existing_last_name);
                    System.out.println("Fetched Username: " + existing_username);
                    System.out.println("Fetched Email: " + existing_email);
                    System.out.println("Fetched Password: " + existing_pswd);
                    System.out.println("Fetched First Name: " + existing_first_name);
                    System.out.println("Fetched Last Name: " + existing_last_name);
    		    	
    		    	// Assigned fetch fields to user object attributes 
    		    	fetch_user.set_User_ID(existing_user_id);
    		    	fetch_user.set_UserName(existing_username);
    		    	fetch_user.set_UserEmail(existing_email);
    		    	fetch_user.set_UserPassword(existing_pswd);
    		    	fetch_user.setFirst_name(existing_first_name);
    		    	fetch_user.setLast_name(existing_last_name);
    		    	// Return
    		    	System.out.println("Return\n\n");
    		    	return fetch_user;
    			}
    	}
    	
    	catch(SQLException e) {
    		System.out.println("An error occured!");
			e.printStackTrace();
			System.out.println(e.getMessage()); 
    	}
    	
    	return fetch_user; 
    }
    
    public User fetch_byUsername(String username) {
    	/**
    	 * @param username to pass onto this method where it uses it as a condition for the query in the database
    	 * @note This method is also one of the official ways where we can retrieve user information along with
    	 * @note fetching user instance by User_ID and by Email
    	 * @return this method returns a single instance of a User object
    	 */
    	
    	// Get an OK connection 
    	connect.getConnection(); 
    	
    	// SQL Statement
    	String SQL = "SELECT * FROM User_tbl WHERE User_name = '"+username+"'"; 
    	System.out.println("SELECT STATEMENT: " + SQL);
    	
    	// Create a User object
    	User fetch_user = new User(); 
    	
    	// Attributes to be assigned
    	String existing_user_id; 
    	String existing_username; 
    	String existing_email; 
    	String existing_pswd;
    	String existing_first_name;
    	String existing_last_name;
    	
    	try {
    		statement = connect.getConnection().createStatement();
    		result = statement.executeQuery(SQL); 
    		
    			// Loop through all rows
    			while(result.next()) {
    				System.out.println("\n\nFinding user...Username -> " + username + " at User_tbl");
    				existing_user_id = result.getString("User_ID");
    				existing_username = result.getString("User_name"); 
    		    	existing_email = result.getString("Email"); 
    		    	existing_pswd = result.getString("User_pswd"); 
    		    	existing_first_name = result.getString("First_name");
    		    	existing_last_name = result.getString("Last_name");
    		    	
    		    	// Print to terminal for being verbose
    		    	System.out.println("Selected ID: " + existing_user_id);
    		    	System.out.println("User Full name : " + existing_first_name + " " + existing_last_name);
                    System.out.println("Fetched Username: " + existing_username);
                    System.out.println("Fetched Email: " + existing_email);
                    System.out.println("Fetched Password: " + existing_pswd);
                    System.out.println("Fetched First Name: " + existing_first_name);
                    System.out.println("Fetched Last Name: " + existing_last_name);
    		    	
    		    	// Assigned fetch fields to user object attributes 
    		    	fetch_user.set_User_ID(existing_user_id);
    		    	fetch_user.set_UserName(existing_username);
    		    	fetch_user.set_UserEmail(existing_email);
    		    	fetch_user.set_UserPassword(existing_pswd);
    		    	fetch_user.setFirst_name(existing_first_name);
    		    	fetch_user.setLast_name(existing_last_name);
    		    	
    		    	// Return
    		    	System.out.println("Return\n\n");
    		    	return fetch_user;
    			}
    	}
    	
    	catch(SQLException e) {
    		System.out.println("An error occured!");
			e.printStackTrace();
			System.out.println(e.getMessage()); 
    	}
    	
    	return fetch_user;
    }
    
    // This method returns an array of User[] objects 
    public User[] get_allStoredUsers() {
    	/**
    	 * @note This will return all the records and list of users from the remote database
    	 * @param Call this method to list out all User data in a table
    	 */
    	
    	// Get an OK connection 
    	connect.getConnection(); 
    	
    	// SQL Statement
    	String SQL = "SELECT * FROM User_tbl"; 
    	System.out.println("SELECT STATEMENT: " + SQL);
    	
    	// Declaring an ArrayList to add all return results from the query
    	ArrayList<User> user_arr_ls = new ArrayList<User>(); 
    	User user_arr_obj = new User(); 
    	
    	// Creating place holders variables for returns of the sub-queries using getString()
		String stored_user_id; 
    	String stored_username; 
    	String stored_email; 
    	String stored_first_name;
    	String stored_last_name;
    	
    	try {
    		statement = connect.getConnection().createStatement();
    		result = statement.executeQuery(SQL); 
    		
    			while(result.next()) {
    				stored_user_id = result.getString("User_ID");
    				stored_username = result.getString("User_name"); 
    				stored_email = result.getString("Email"); 
    				stored_first_name = result.getString("First_name");
    				stored_last_name = result.getString("Last_name");
    				
    				// Assign the return field values by the sub-queries
    				user_arr_obj = new User(); 
    				user_arr_obj.set_User_ID(stored_user_id);
    				user_arr_obj.set_UserName(stored_username);
    				user_arr_obj.set_UserEmail(stored_email);
    				user_arr_obj.setFirst_name(stored_first_name);
    				user_arr_obj.setLast_name(stored_last_name);
    				
    				// Add all instances to the array list
    				user_arr_ls.add(user_arr_obj); 
    				
    				// Verbose 
    				System.out.println("Selected ID: " + stored_user_id);
    		    	System.out.println("User Full name : " + stored_first_name + " " + stored_last_name);
                    System.out.println("Fetched Username: " + stored_username);
                    System.out.println("Fetched Email: " + stored_email);
                    System.out.println("Fetched First Name: " + stored_first_name);
                    System.out.println("Fetched Last Name: " + stored_last_name);
                    
    			}
    	}
    	
    	catch(SQLException e) {
    		System.out.println("An error occured!");
			e.printStackTrace();
			System.out.println(e.getMessage()); 
    	}
    	
    	
    	return (User []) user_arr_ls.toArray(new User[user_arr_ls.size()]);
    }
    
    // ==================================== END METHODS FOR USER TABLE ============================== // 
    
    
    
    
    
    /**
     * Methods responsible for User_tbl
     */
    // ==================================== BEGIN METHODS FOR USER TABLE ============================== // 
    
    // This method can insert products contents into the remote database
    public void product_insert_newData(Product product) {
    	/**
    	 * @note Insert and injects product contents into the Product_tbl at the remote database
    	 * @param Pass the product object instance to this to handle the insertion of product content data
    	 */
    	// Get an OK connection 
    	connect.getConnection(); 
    	
    	// SQL statement
    	String SQL = "INSERT INTO Product_tbl(Product_code, Product_name, Price) VALUES(" +
    				 "'" +product.getProduct_code()+ "'" + "," +
    				 "'" +product.getProduct_name()+ "'" + "," +
    				  	 +product.getProduct_price()+ ")";
    	
    	System.out.println("INSERT STATEMENT: " + SQL);
    	
    	try {
    		statement = connect.getConnection().createStatement();
            statement.executeUpdate(SQL);
            statement.close();
    	}
    	
    	catch(SQLException e) {
    		System.out.println("An error occured!");
			e.printStackTrace();
			System.out.println(e.getMessage()); 
    	}
    }
    
    // This method can update the resources of Product
    public void product_update_data(String PK_id, String col, String val_update) {
    	/**
    	 * @param PK_id pass the PK User_ID to set the WHERE clause
    	 * @param col select the column to update from the User_tbl
    	 * @param val_update insert and update the new value
    	 */
    	// Get an OK connection 
    	connect.getConnection(); 
    	
    	// SQL Statement
    	String SQL = "UPDATE Product_tbl"
    				+ " SET " + col + " = " + "'" + val_update + "'"
    				+ " WHERE User_ID = " + "'" + PK_id + "'";
    	
    	System.out.println("UPDATE STATEMENT: " + SQL);
    	
    	try {
    		statement = connect.getConnection().createStatement();
            statement.executeUpdate(SQL);
            statement.close();
    	}
    	
    	catch (SQLException e) {
    		System.out.println("An error occured!");
			e.printStackTrace();
			System.out.println(e.getMessage()); 
    	}
    }
    
    // This method returns all the stored product items from the database
    public Product[] viewItemInventory() {
    	/**
    	 * @note This will return an object array of the Product class
    	 */
    	// Get an OK connection 
    	connect.getConnection(); 
    	
    	// SQL Statement
    	String SQL = "SELECT * FROM Product_tbl"; 
    	System.out.println("SELECT STATEMENT: " + SQL);
    	
    	// Declaring an ArrayList to add all return results from the query
    	ArrayList<Product> product_ls = new ArrayList<Product>(); 
    	// Declare a Product object and create an instance
    	// Product(String product_name, String product_brand, double price)
    	Product product_obj = new Product(); 
    	
    	// Creating place holders variables for returns of the sub-queries using getString()
		String stored_prod_id; 
		String stored_prod_name;
    	double stored_prod_price; 
    	
    	try {
    		statement = connect.getConnection().createStatement();
    		result = statement.executeQuery(SQL); 
    		
    			while(result.next()) {
    				stored_prod_id = result.getString("Product_code");
    				stored_prod_name = result.getString("Product_name"); 
    				stored_prod_price = result.getDouble("Price");
    				
    				// Always create a new instance upon the while loop
    				product_obj = new Product(); 
    				
    				// Assign the return field values by the sub-queries
    				product_obj.setProduct_code(stored_prod_id);
    				product_obj.setProduct_name(stored_prod_name);
    				product_obj.setProduct_price(stored_prod_price);
    				
    				// Add all instances to the array list
    				product_ls.add(product_obj); 
    				
    				// Verbose 
    				System.out.println("Selected ID: " + stored_prod_id);
                    System.out.println("Fetched Product Name: " + stored_prod_name);
                    System.out.println("Fetched Price: " + stored_prod_price);
    			}
    	}
    	
    	catch(SQLException e) {
    		System.out.println("An error occured!");
			e.printStackTrace();
			System.out.println(e.getMessage()); 
    	}
    	
    	
    	return (Product []) product_ls.toArray(new Product[product_ls.size()]);
    }
    
    // This method returns a single instance of a Product 
    public Product fetch_product_item(String product_name) {
    	/**
    	 * @param product_name to pass where it will be used as in the where clause
    	 * @note this method returns one instance of a product object
    	 */
    	// Get an OK connection 
    	connect.getConnection(); 
    	
    	// SQL Statement
    	String SQL = "SELECT * FROM Product_tbl WHERE Product_name= '"+product_name+"'"; 
    	System.out.println("SELECT STATEMENT: " + SQL);
    	
    	// Create a Product object
    	Product fetch_product = new Product(); 
    	
    	try {
    		statement = connect.getConnection().createStatement();
    		result = statement.executeQuery(SQL); 
    		
    			// Loop through all rows
    			while(result.next()) {
    				System.out.println("\n\nFinding Product...Product_name -> " + product_name + " at User_tbl");
    				System.out.println("Product_ID: " + result.getString("Product_code"));
    				System.out.println("Product_name: " + result.getString("Product_name"));
    				System.out.println("Price: " + result.getString("Price"));
    		   
    				// Verbose 
    				System.out.println("Selected ID: " + result.getString("Product_code"));
                    System.out.println("Fetched Product Name: " + result.getString("Product_name"));
                    System.out.println("Fetched Price: " + result.getDouble("Price"));
    				
    		    	// Assigned fetch fields to user object attributes 
    		    	fetch_product.setProduct_code(result.getString("Product_code"));
    		    	fetch_product.setProduct_name(result.getString("Product_name"));
    		    	fetch_product.setProduct_price(result.getDouble("Price")); 
    		    	
    		    	// Return
    		    	System.out.println("Return\n\n");
    		    	return fetch_product;
    			}
    			
    	}
    	
    	catch(SQLException e) {
    		System.out.println("An error occured!");
			e.printStackTrace();
			System.out.println(e.getMessage()); 
    	}
    	
    	return fetch_product; 
    }
    
    // This method should return only the item which states the name of the product_name
    public String get_names(String product_name) {
    	/**
    	 * @note This can fetch and return the Product_name when selecting a particular item
    	 * @param Product_name to pass as a string from a chosen sets of inputs
    	 * @return String get_names
    	 */
    	// Get an OK connection 
    	connect.getConnection(); 
    	
    	String names = null; 
    	
    	// SQL Statement
    	String SQL = "SELECT * FROM Product_tbl WHERE Product_name = " + "'" + product_name + "'"; 
    	System.out.println("SELECT STATEMENT: " + SQL);
		
		try {
			statement = connect.getConnection().createStatement();
			result = statement.executeQuery(SQL); 
				
				while(result.next()) {
					// Begin assigning attributes
					names = result.getString("Product_name"); 
					
					// Verbose 
    				System.out.println("Selected ID: " + result.getString("Product_code"));
                    System.out.println("Fetched Product Name: " + result.getString("Product_name"));
                    System.out.println("Fetched Price: " + result.getDouble("Price"));
					
				}
			
		}
		
		catch(SQLException e) {
			System.out.println("An error occured!");
			e.printStackTrace();
			System.out.println(e.getMessage()); 
		}
    	
    	return names;
    }
    
    // This method should return only the item which states the name of the product_name
    public double get_price(String product_name) {
    	/**
    	 * @param Product_name to pass in which will be used in the where clause of SQL 
    	 * @return Double price
    	 */
    	// Get an OK connection 
    	connect.getConnection(); 
    	
    	double price = 0; 
    	
    	// SQL Statement
    	String SQL = "SELECT * FROM Product_tbl WHERE Product_name = " + "'" + product_name + "'"; 
    	System.out.println("SELECT STATEMENT: " + SQL);
		
		try {
			statement = connect.getConnection().createStatement();
			result = statement.executeQuery(SQL); 
				
				while(result.next()) {
					// Begin assigning attributes
					price = result.getDouble("Price"); 
					
					// Verbose 
    				System.out.println("Selected ID: " + result.getString("Product_code"));
                    System.out.println("Fetched Product Name: " + result.getString("Product_name"));
                    System.out.println("Fetched Price: " + result.getDouble("Price"));
                    
                    return price;
				}
			
		}
		
		catch(SQLException e) {
			System.out.println("An error occured!");
			e.printStackTrace();
			System.out.println(e.getMessage()); 
		}
    	
    	return price;
    }
    
    // ==================================== END METHODS FOR PRODUCT TABLE ============================== // 
 
    
    
    
    
    /**
     * Methods responsible for Cart_tbl
     */
    // ==================================== BEGIN METHODS FOR CART TABLE ============================== // 
    
    // This method inserts the cart data where the user has chosen their picks of items
    public void cart_insert_newData(Cart cart, Product[] product) {
    	/**
    	 * @param Pass the instances of product object here. It should be an array of Product class
    	 * @note This should properly
    	 */
    	// Get an OK connection 
    	connect.getConnection(); 
    	
    	// Loop through how much data there is on the product object arrays
    	for (int i = 0; i < product.length; i++) {    		
    		// SQL statement
    		String SQL = "INSERT INTO Cart_tbl(Cart_ID, Product_code_at_cart, Product_name, Price) VALUES(" +
    				"'" +cart.getCart_ID()+ "'" + "," +
    				"'" +product[i].getProduct_code()+ "'" + "," +
    				"'" +product[i].getProduct_name()+ "'" + "," +
    					+product[i].getProduct_price()+ ")";
    		
    		
    		System.out.println("INSERT STATEMENT: " + SQL);
        	
	        	try {
	        		statement = connect.getConnection().createStatement();
	                statement.executeUpdate(SQL);
	                statement.close();
	        	}
	        	
	        	catch(SQLException e) {
	        		System.out.println("An error occured!");
	    			e.printStackTrace();
	    			System.out.println(e.getMessage()); 
	        	}
    	}

    }
    
    
    
    // ==================================== END METHODS FOR CART TABLE ============================== // 
    
    
    
    
    
}
