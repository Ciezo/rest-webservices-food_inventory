/*
    Document   : OAuthTest.java
    Package	   : Proxy.src.oauth;
    Created on : July 27, 2022
    Author     : Cloyd Van S. Secuya
    Description:
    	Test the capabilities and methods of the Authentication Service
*/

// PACKAGE SECTION
package oauth.test;


// IMPORT SECTION
import com.db.driver.Driver;
import com.db.driver.ExecQuery;
import oauth.AuthenticationService;
import resource.User;


public class OAuthTest {
	
	public static void main(String[] args) {
		Driver driver = new Driver(); 
		User user;			// This user object is tested TO PASS the Authentication Service because it is in the database see the driver
		User user2;			// While this one is tested TO FAIL because it is not in the database
		
		
		/**
		 * @testCaseDesc test the following conditions to make sure the AuthenticationService is working
		 * @testCases1 user will pass
		 * @testCases2 user2 will fail 
		 */
		
		/**
		 * @testCases1
		 */
		// ============================ BEGIN TEST CASE 1 ============================ 
		System.out.println("TEST CASE 1 -> AuthService Reliability");
		user = new User("Jake the Dog", "adventure_time@ooo.com", "secretpassword", "Jake", "Dog");
		// System.out.println("Inserting into database...."); 
		 /**
		  * @note user : Jake the Dog is ALREADY INSERTED TO DATABASE...
		  * @what we are trying to do is Authenticate him
		  */
		   driver.user_insert_newData(user);
		  AuthenticationService auth = new AuthenticationService(user.get_UserName(), user.get_UserPassword()); 
		  
		  /**
		   * @note There are two ways we can check the verification of our user. 
		   * @note both of these methods return boolean
		   * 		1. checkCredsValidity(void)     - this checks the User_name, Email, and User_ID
		   * 		2. checkUserValidity(User user) - this checks the whole user object just like what we see above
		   */
		  
		  // Running both methods can work well. But I believe it is much preferred to use checkUserValidity(User user)
		  auth.checkCredsValidity();
		  auth.checkUserValidity(user);			// Because this checks all the assigned attributes of a user object against the DB
		 // AuthenticationService auth = new AuthenticationService(user.get_User_ID(), user.get_UserName(), user.get_UserEmail()); 
		// ============================ END TEST CASE 1 ============================ 
		
		
		
		/**
		 * @testCases2
		 */
		// ============================ BEGIN TEST CASE 2 ============================
		System.out.println("\n\n\nTEST CASE 2 -> AuthService Reliability");
		user2 = new User("Ice King", "ice_world@ooo.com", "secretpassword", "Simon", "Petrikov");
		/**
		 * @note Ice King should return fail or something. 
		 * @note And with that we are able to determine if a user is registered or not
		 */
		// auth = new AuthenticationService(user2.get_UserName(), user2.get_UserPassword());
		auth = new AuthenticationService(user2.get_User_ID(), user2.get_UserName(), user2.get_UserEmail(), user2.get_UserPassword()); 
		// auth.checkCredsValidity();			// The program fails and stops because there are no values returned from the database
											/// Because user2 does not exist.
		auth.checkUserValidity(user2);
		// ============================ END TEST CASE 2 ============================ 
		
	}
	
}
