/*
    Document   : KeyGeneration.java
    Package	   : Database.src.resource;
    Created on : July 25, 2022
    Author     : Cloyd Van S. Secuya
    Description:
    	This is the Java source file responsible for generating the IDs, Keys, and unique sequences.
		Moreover, it will assign IDs to the following: 
			1. product_code: Product 
			2. cart_ID: Cart
			3. user_id: User
*/

// PACKAGE
package resource;


// IMPORT SECTION
import java.util.Random;

public class KeyGeneration {
	
	private String key_code; 
	private String key; 
	
	// Setting the maximum number to generate
	int MAX_THRESHOLD = 1000; 
	
	// Set max salt string
	final int MAX_SALT = 5; 
	
    // Random class for implementing RGN
    private Random random = new Random(); 
    private int rgn = random.nextInt(MAX_THRESHOLD); 
    
    // A method to set the RGN; not more than the MAX_THRESHOLD
    //	then, assign the generated number to our item_code
    public void setRGN() {
        random = new Random(); 				
        rgn = random.nextInt(MAX_THRESHOLD);
    }
    
    // Return the generated RGN
    public int getRGN() {
    	return rgn;
    }
    
    // We are to create a unique String key to which will concatenate to the RGN 
    /**
     * @NOTE: This truly can have a very unique item_code
     * 		  item_code = RGN + KEY 
     * 			item_code is what we want to return
     */
    public String genKEY() {        
        String KEY = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
        		   + "abcdefghijklmnopqrstuvwxyz";
        
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        
        // Set it to less than 20 characters
        /**
         * We can set this other number as well.
         */
        while (salt.length() < MAX_SALT) { 
            int index = (int) (rnd.nextFloat() * KEY.length());
            salt.append(KEY.charAt(index));
        }
        String saltStr = salt.toString();        
        
        key = Integer.toString(rgn); 
        key_code = key + saltStr;
        
        return key_code;
    }
}
