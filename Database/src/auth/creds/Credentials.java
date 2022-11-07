/*
    Document   : Credentials.java
    Package	   : Database.src.auth.creds;
    Created on : July 26, 2022
    Author     : Cloyd Van S. Secuya
    Description:
    	This source file is responsible for defining the Database Credentials where the JDBC driver
    	can connect to. 
*/

// PACKAGE
package auth.creds;


public class Credentials {
	
	private String DATBASE_URL; 
	private String HOSTNAME;
	private int PORT; 
	private String DATABASE; 
	
	private String USERNAME;
	private String PASSWORD;
	private String URI;
	
	
	public Credentials() {
		/**
		 * @note All default credentials will be assigned upon initialization
		 */
		this.DATBASE_URL = "postgres://lemzyextdjbmcj:8da3716509fdd2d9e072ec173129a64342f9539bb1e8260f58a07d91bf135c07@ec2-18-214-35-70.compute-1.amazonaws.com:5432/d5oktcpujqqcse";
		this.HOSTNAME = "ec2-18-214-35-70.compute-1.amazonaws.com";
		this.PORT = 5432;
		this.DATABASE = "d5oktcpujqqcse"; 
		
		this.USERNAME = "lemzyextdjbmcj"; 
		this.PASSWORD = "8da3716509fdd2d9e072ec173129a64342f9539bb1e8260f58a07d91bf135c07"; 
		this.URI = "postgres://lemzyextdjbmcj:8da3716509fdd2d9e072ec173129a64342f9539bb1e8260f58a07d91bf135c07@ec2-18-214-35-70.compute-1.amazonaws.com:5432/d5oktcpujqqcse"; 
	}
	
	/**
	 * Getters Methods to return all initialized values assigned upon calling the default constructor
	 */
	public String getDATBASE_URL() {
		return DATBASE_URL;
	}

	public String getHOSTNAME() {
		return HOSTNAME;
	}

	public int getPORT() {
		return PORT;
	}

	public String getUSERNAME() {
		return USERNAME;
	}

	public String getPASSWORD() {
		return PASSWORD;
	}

	public String getURI() {
		return URI;
	}

	public String getDATABASE() {
		return DATABASE;
	}
	
}
