/*
    Document   : User.java
    Package	   : WebServices.src.resource.ws;
    Created on : July 25, 2022
    Author     : Cloyd Van S. Secuya
    Description:
    	This describes the characteristics of the User. 
    	Our users are our clients.
*/

// PACKAGE
package net.resources.ws;


public class User {

	private String user_ID; 
	private String user_name;
	private String email;
	private String password;
	private String first_name; 
	private String last_name;
	
	// Key Generation Algorithm
	private KeyGeneration keygen = new KeyGeneration(); 
		
	public User(String user_name, String email, String password, String first_name, String last_name) {
		/**
		 * @param String user_name : pass the user_name to set and instantiate
		 * @param String email	: pass the email 
		 * @param String password: pass and set the password attribute
		 * @param String first_name : pass the first name
		 * @param String last_name : pass the last_name
		 * @note the user_ID is automatically assigned upon instantiation
		 */
		
		// Begin setting up the user_ID
		keygen.setRGN();
		// Assign the generated alphanumeric sequence
		this.user_ID = keygen.genKEY(); 
		
		this.user_name = user_name;
		this.email = email;
		this.password = password;
		this.first_name = first_name;
		this.last_name = last_name;
	}
	
	public User() {
		/**
		 * @default upon using the default constructor we automatically set up the user_ID
		 */
		// Begin setting up the user_ID
		keygen.setRGN();
		// Assign the generated alphanumeric sequence
		this.user_ID = keygen.genKEY(); 
	}

	/**
	 * Getters and Setters for manually assigning attributes
	 */
	
	public void set_User_ID(String ID) {
		this.user_ID = ID;
	}
	
	public String get_User_ID() {
		return user_ID; 
	}
	
	public void set_UserName(String name) {
		this.user_name = name; 
	}
	
	public String get_UserName() {
		return user_name;
	}
	
	public void set_UserEmail(String email) {
		this.email = email; 
	}
	
	public String get_UserEmail() {
		return email;
	}
	
	public void set_UserPassword(String password) {
		this.password = password;
	}
	
	public String get_UserPassword() {
		return password;
	}

	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}
	
	public String getFirst_name() {
		return first_name;
	}

	public String getLast_name() {
		return last_name;
	}

	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}
	
}
