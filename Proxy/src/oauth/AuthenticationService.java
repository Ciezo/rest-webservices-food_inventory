/*
    Document   : AuthenticationServices.java
    Package	   : Proxy.src.oauth;
    Created on : July 26, 2022
    Author     : Cloyd Van S. Secuya
    Description:
    	This authenticates the user registration, logging on, and account creation.
    	It has the capability to register the user when it does not exist in the database.
    	Moreover, it accesses the Driver to communicate with the fetching of data from the database.
    	
    	Checks if the user exists in the database, and follows these conditions: 
    		1. Allow user to access the Client application if they have successfully logged-in
    		2. Allow user to register their account if they do not have an existing record in the database.
    		3. Allow logging in of existing users.
*/

// PACKAGE
package oauth;


// IMPORT SECTION
import java.awt.HeadlessException;
// IMPORT SECTION
import java.awt.Toolkit;
import javax.swing.JOptionPane;
import com.db.driver.Driver;
import resource.User;


public class AuthenticationService {

	private String user_id;
	private String user_name;
	private String email; 
	private String password;
	private boolean check = false;
	
	Driver driver = new Driver(); 
	
	public AuthenticationService(String user_id, String user_name, String email, String password) {
		/**
		 * @note This class upon using initializing this constructor will assigned the passed attributes
		 * @param user_ID to fetch from the database
		 * @param user_name to find and search through the database
		 * @param email of the user to for authentication purposes as well
		 */
		this.user_id = user_id;
		this.user_name = user_name; 
		this.email = email;
		this.password = password;
	}
	
	public AuthenticationService(String user_name, String password) {
		/**
		 * @note This can help to pass the specific attributes to authenticate 
		 * @note while the rest of the attributes can be assigned by the driver which fetches the instances based on the user_name alone
		 */
		this.user_name = user_name;
		this.password = password;
	}
	
	public AuthenticationService(User user) {
		/**
		 * @note This constructor upon initialization will assign the attributes of the AuthenticationService
		 * @param user: User pass the whole instantiation of a User object to begin extracting the assigned values
		 */		
		this.user_id = user.get_User_ID(); 
		this.user_name = user.get_UserName(); 
		this.email = user.get_UserEmail(); 
		this.password = user.get_UserPassword(); 
	}
	
	
	public AuthenticationService() {}

	/**
	 * Getters Methods to access private attributes
	 */
	
	public String getUser_id() {
		return user_id;
	}

	public String getUser_name() {
		return user_name;
	}

	public String getEmail() {
		return email;
	}
	
	public String getPassword() {
		return password;
	}
	
	// This method will check the validity of the passed attributes to the AuthenticationService class
	public boolean checkCredsValidity() {
		/**
		 * @note This method checks the validity of the attributes passed on the AuthenticationService()
		 * @return true if the user has a record in the database
		 * @return false if the user does not have a record in the database
		 */
				
		this.user_id = driver.fetch_byUsername(this.getUser_name()).get_User_ID(); 
		this.email = driver.fetch_byUsername(this.getUser_name()).get_UserEmail();
		
		// Create a user object and is used to be assigned on returned records from the database
		User user_byID; 
		user_byID = driver.user_fetch_DataByID(this.getUser_id()); 
		
		User user_byEmail;
		user_byEmail = driver.user_fetch_byEmail(this.getEmail());
		
		
		// Check by Email to Authenticate
		try {
			if ( !(this.email.isEmpty()) ) {
				System.out.println("\n\n\nCHECKING VALIDITY...OF THE FOLLOWING: ");
				System.out.println("\t===>> Username: " + this.getUser_name());
				System.out.println("\t===>> Password: " + this.getPassword() +"\n\n");
				
				
					try {
							if ( (user_byEmail.get_UserEmail().equals(this.getEmail())) || 
									(user_byEmail.get_User_ID().equals(this.getUser_id())) && 
										(user_byEmail.get_UserName().equals(this.getUser_name())) && 
											(user_byEmail.get_UserPassword().equals(this.getPassword())) ) 
							{
												// Print to terminal for results and verbose purposes
												System.out.println("---->> EXISTING RECORDS FOUND! <<----");
												System.out.println("PROCESSING EMAIL...." + user_byID.get_UserEmail());
												System.out.println("PROCESSING ID...." + user_byID.get_User_ID());
												System.out.println("PROCESSING USERNAME...." + user_byID.get_UserName());
												check = true;
												System.out.println("VALIDITY REQUEST: " + check);
												return check;
							}
							
							else 
							{
								check = false;
								System.out.println("VALIDITY REQUEST: " + check);
								System.out.println("USER NOT FOUND WITHIN THE DATABASE!");
								return check;
							}	
							
					} 
					
					catch (Exception e) {
						System.out.println("\n\n\t THE AUTHENTICATION SERVICE HAS FOUND OUT THAT IS USER MAY NOT EXIST WITHIN THE DATABASE!");
						e.getMessage();
						e.printStackTrace();
					}
			}
		}
		
		catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Authentication Service has not found any existing credentials", "NOTICE!", JOptionPane.ERROR_MESSAGE);
			try {Thread.sleep(300);} catch (InterruptedException ex) {ex.printStackTrace();}
			JOptionPane.showMessageDialog(null, "User name is not found within the remote database", "NOTICE!", JOptionPane.ERROR_MESSAGE);
			try {Thread.sleep(300);} catch (InterruptedException ex) {ex.printStackTrace();}
			JOptionPane.showMessageDialog(null, "Email is not found within the remote database", "NOTICE!", JOptionPane.ERROR_MESSAGE);
			try {Thread.sleep(300);} catch (InterruptedException ex) {ex.printStackTrace();}
			Toolkit.getDefaultToolkit().beep();
			JOptionPane.showMessageDialog(null, "Are you sure your account is registered?", "USER ALERT!", JOptionPane.INFORMATION_MESSAGE);
		}
		
		
		
		
		
		
		
		// Check by User_ID to Authenticate
		try {
			if ( !(this.user_id.isEmpty()) ) {
				System.out.println("\n\n\nCHECKING VALIDITY...OF THE FOLLOWING: ");
				System.out.println("\t===>> User ID: " + this.getUser_id());
				System.out.println("\t===>> User Email: " + this.getEmail());
				System.out.println("\t===>> Username: " + this.getUser_name() +"\n\n");
				
				
					try {
							if ( (user_byID.get_User_ID().equals(this.getUser_id())) || 
									(user_byID.get_UserEmail().equals(this.getEmail())) && 
										(user_byID.get_User_ID().equals(this.getUser_id())) && 
											(user_byID.get_UserPassword().equals(this.getPassword())) ) 
							{
												// Print to terminal for results and verbose purposes
												System.out.println("---->> EXISTING RECORDS FOUND! <<----");
												System.out.println("PROCESSING ID...." + user_byID.get_User_ID());
												System.out.println("PROCESSING EMAIL...." + user_byID.get_UserEmail());
												System.out.println("PROCESSING USERNAME...." + user_byID.get_UserName());
												check = true;
												System.out.println("VALIDITY REQUEST: " + check);
												return check;
							}
							
							else 
							{
								check = false;
								System.out.println("VALIDITY REQUEST: " + check);
								System.out.println("USER NOT FOUND WITHIN THE DATABASE!");
								return check;
							}
							
					} 
					
					catch (Exception e) {
						System.out.println("\n\n\t THE AUTHENTICATION SERVICE HAS FOUND OUT THAT IS USER MAY NOT EXIST WITHIN THE DATABASE!");
						e.getMessage();
						e.printStackTrace();
					}
				
			}
		}
		
		catch (Exception e) {
			e.printStackTrace();
		}
		
		
		System.out.println("Is user authenticated and recorded in the database? "); 
		return check;
	}
	
	// This method checks the User itself where it tries to fetch the instance of a local user object
	public boolean checkUserValidity(User user) {
		/**
		 * @param user: User where the instance of the whole User object is to be checked the by AuthenticationService
		 * @note This method determines and checks the user whether they have an instance of a record in the database
		 * @return True if the User object exists within the database
		 * @return False if there is no record found within the database
		 */
		
		// Call the driver to handle the fetching of a user record and create an instance of a user
		User to_validate; 
		to_validate = driver.fetch_byUsername(user.get_UserName()); 
		
		// Check the username to authenticate
		try {
			if ( !(this.user_name.isEmpty())) {
				System.out.println("\n\n\nCHECKING VALIDITY...OF THE FOLLOWING: ");			
				System.out.println("\t===>> User ID: " + this.getUser_id());
				System.out.println("\t===>> Username: " + this.getUser_name());
				System.out.println("\t===>> Email: " + this.getEmail());
				System.out.println("\t===>> Password: " + this.getPassword() + "\n\n");
				
					try {
						
							if ( (to_validate.get_User_ID().equals(this.getUser_id())) || 
									(to_validate.get_UserEmail().equals(this.getEmail())) && 
										(to_validate.get_User_ID().equals(this.getUser_id())) && 
											to_validate.get_UserPassword().equals(this.getPassword())) 
							{
												// Print to terminal for results and verbose purposes
												System.out.println("---->> EXISTING RECORDS FOUND! <<----");
												System.out.println("PROCESSING ID...." + to_validate.get_User_ID());
												System.out.println("PROCESSING EMAIL...." + to_validate.get_UserEmail());
												System.out.println("PROCESSING USERNAME...." + to_validate.get_UserName());
												System.out.println("PROCESSING PASSWORD...." + to_validate.get_UserPassword());
												check = true;
												System.out.println("VALIDITY REQUEST: " + check);
												return check;
							}
							
							else 
							{
								check = false;
								System.out.println("VALIDITY REQUEST: " + check);
								System.out.println("USER NOT FOUND WITHIN THE DATABASE!");
								return check;
							}
					}
					
					catch (Exception e) {
						System.out.println("\n\n\t THE AUTHENTICATION SERVICE HAS FOUND OUT THAT IS USER MAY NOT EXIST WITHIN THE DATABASE!");
						e.getMessage();
						e.printStackTrace();
					}
			}
		} 
		
		catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Authentication Service has not found any existing credentials", "NOTICE!", JOptionPane.ERROR_MESSAGE);
			try {Thread.sleep(300);} catch (InterruptedException ex) {ex.printStackTrace();}
			JOptionPane.showMessageDialog(null, "User name is not found within the remote database", "NOTICE!", JOptionPane.ERROR_MESSAGE);
			try {Thread.sleep(300);} catch (InterruptedException ex) {ex.printStackTrace();}
			JOptionPane.showMessageDialog(null, "Email is not found within the remote database", "NOTICE!", JOptionPane.ERROR_MESSAGE);
			try {Thread.sleep(300);} catch (InterruptedException ex) {ex.printStackTrace();}
			Toolkit.getDefaultToolkit().beep();
			JOptionPane.showMessageDialog(null, "Are you sure your account is registered?", "USER ALERT!", JOptionPane.INFORMATION_MESSAGE);
			
			e.printStackTrace();
		}
		
		System.out.println("Is user authenticated and recorded in the database? "); 
		return check;
	}
	
}
