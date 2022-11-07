/*
    Document   : EstablishConnection.java
    Package	   : Proxy.src.com.db.conn;
    Created on : July 26, 2022
    Author     : Cloyd Van S. Secuya
    Description:
    	This is Java Class file is responsible for connecting to the remote PostgreSQL server.
    	It establishes connection using the PostgreSQL JDBC driver which can be found at
    	../lib/depedencies/driver/psql/
*/

// PACKAGE SECTION
package com.db.conn;


// IMPORT SECTION 
import java.sql.*;
import auth.creds.Credentials;


public class EstablishConnection {
	
	// Define a connection object from java.sql
	private Connection conn = null; 
	
	// Instantiate all the database credentials
	private Credentials creds = new Credentials(); 
	
	// Database parameters
	final String host = creds.getHOSTNAME(); 
	final String port = String.valueOf(creds.getPORT()); 
	final String database = creds.getDATABASE();
	
	public EstablishConnection() {
		
		/**
		 * @param Upon calling the default constructor it initializes the connection to the remote server with the DriverManager and JDBC
		 * @method connect_SUCCESS() returns a String of "SUCCESS" if connection is established 
		 * @method connect_FAIL() returns a String of "FAILED" if there is no connection
		 * @note The default database paramaters will be assigned from the Credentials.java
		 */
		
		// Create a try-catch block to handle any user errors
		try {
			
			// Driver set-up and initialization 
			DriverManager.registerDriver(new org.postgresql.Driver());
			 
			/**
			 * Begin establishing connection to remote Heroku server
			 */
			// Print to terminal to view URI
            System.out.println("\n\nSetting up default connection to " + creds.getURI());
            System.out.println("Path found at ==>> " + "jdbc:postgresql://" + host + ":" + port + "/" + database);
            System.out.println("JDBC URL PATH ==>> " + "jdbc:postgresql://" + host + ":" + port + "/" + database + creds.getUSERNAME() + creds.getPASSWORD());
            conn = DriverManager.getConnection("jdbc:postgresql://" + host + ":" + port + "/" + database, creds.getUSERNAME(), creds.getPASSWORD()); 
            
            
            	// Set up conditions to see if connection establishment is successful 
		            if (conn != null) {
		            	connect_SUCCESS();
		            }
		            
		            else {
		            	connect_FAIL();
		            }
			
		}
		
		catch(SQLException e) {
			System.out.println("An error occured!");
			e.printStackTrace();
			System.out.println(e.getMessage()); 
		}		
	}
	
	public Connection getConnection() {
        /**
         * @note This returns the newly established handshake connection
         */
		return conn; 
    }
	
	public String connect_SUCCESS() {
		/**
		 * @return Returns SUCCESS if there is a connection to the path at remote server
		 */
		System.out.println("HANDSHAKE ESTABLISHED!");
		return "SUCCESS"; 
	}
	
	public String connect_FAIL() { 
		/**
		 * @return Returns FAIL if there is no connection established
		 */
		System.out.println("HANDSHAKE FAILED!");
		return "FAIL"; 
	}
	
	public static void main(String[] args) {
        new EstablishConnection();
    }
}
