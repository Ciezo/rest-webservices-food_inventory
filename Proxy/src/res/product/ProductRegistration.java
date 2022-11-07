/*
    Document   : ProductRegistration.java
    Package	   : Proxy.res.product;
    Created on : August 4, 2022
    Author     : Cloyd Van S. Secuya
    Description:
    	Inject all the products into the database
*/

// PACKAGE
package res.product;


// IMPORT SECTION
import resource.Product;
import com.db.driver.Driver;


public class ProductRegistration {
	
	// Product(String product_name, String product_brand, double price)
	
	
	public static void main(String[] args) {
		
		Driver driver = new Driver();
		
		Product[] appetizers = new Product[] {
				new Product("Calamari", 199.75),
				new Product("Chips", 99.75),			
				new Product("Layonnaise",  249.50),			
				new Product("Nuts",  49.25),			
				new Product("Potato Salad", 174.55),			
				new Product("Salmon Canape", 169.40),			
		};
		
		Product[] main_dish = new Product[] {
				new Product("Adobo",  299.00),
				new Product("Lasagna",  289.25),			
				new Product("Risotto",  249.50),			
				new Product("Salmon", 229.35),			
				new Product("Sea Food Platter", 449.59),			
				new Product("Steak",  499.90),			
		};
		
		Product[] sides = new Product[] {
				new Product("Gamjajeon",  249.99),
				new Product("Gyoza",  139.30),			
				new Product("Kimchi", 229.10),			
				new Product("Natto", 69.69),			
				new Product("Sushi",  89.99),			
				new Product("Tamagoyaki", 499.90),			
		};
		
		Product[] desserts = new Product[] {
				new Product("Coffee Jelley",  119.99),
				new Product("Halo-Halo",  99.00),			
				new Product("Leche Flan", 229.50),			
				new Product("Pannettone",  169.69),			
				new Product("Panna Cotta",  229.50),			
				new Product("Tiramisu", 209.50),			
		};
		
		
		/**
		 * Begin injecting into database with driver
		 * @note RUN ONCE ONLY!!!!
		 */
		
		// Insert the appetizers 
		for (int i = 0; i < appetizers.length; i++) {
			driver.product_insert_newData(appetizers[i]);
		}
		
		// Insert the main_dish
		for (int i = 0; i < main_dish.length; i++) {
			driver.product_insert_newData(main_dish[i]);
		}
		
		// Insert the sides
		for (int i = 0; i < sides.length; i++) {
			driver.product_insert_newData(sides[i]);
		}
		
		// Insert the desserts
		for (int i = 0; i < desserts.length; i++) {
			driver.product_insert_newData(desserts[i]);
		}
	}
	
}
