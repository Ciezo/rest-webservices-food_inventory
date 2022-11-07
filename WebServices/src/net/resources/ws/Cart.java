/*
    Document   : Cart.java
    Package	   : WebServices.net.resource.ws;
    Created on : July 25, 2022
    Author     : Cloyd Van S. Secuya
    Description:
    	The Cart class has a tight relationship with the Product class. 
    	This class can take the objects of the Product class and be able to store it into an object array.
*/

// PACKAGE
package net.resources.ws;


// IMPORT SECTION
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class Cart {

	private String cart_ID; 
	private Product[] product = new Product[]{};
	private Product product_item;
	private String product_code_at_cart;
	private String product_name;
	private double price;
	
	// Key Generation Algorithm
	private KeyGeneration keygen = new KeyGeneration(); 

	Map <String, Product> product_map = new HashMap<String, Product>(); 
	
	public Cart(Product[] product_add_to_cart) {
		/**
		 * @param Product[] product : pass the product object as array
		 * @note the cart_ID is automatically generated.
		 */
		// Begin setting up the cart_ID
		keygen.setRGN();
		this.cart_ID = keygen.genKEY(); 
		this.product = product_add_to_cart;
	}
	
	public Cart(String cart_ID, Product[] product_add_to_cart) {
		/**
		 * @param String cart_ID : pass your manually set cart_ID
		 * @param Product[] product: pass the product object array
		 */
		this.cart_ID = cart_ID; 
		this.product = product_add_to_cart;
	}
	
	public Cart(Product product) {
		// Begin setting up the cart_ID
		keygen.setRGN();
		this.cart_ID = keygen.genKEY(); 
		this.product_item = product;
	}
	
	public Cart() {
		/**
		 * @default this creates a new instantiation of the Cart
		 */
	} 
	
	
	/**
	 * Getters and Setters to manually assign attributes 
	 */
	
	public void setCart_ID(String id) {
		this.cart_ID = id; 
	}
	
	public String getCart_ID() {
		return cart_ID;
	}
		
	public void setProduct_ArrObj(Product[] product) {
		this.product = product;
	}
	
	public Product[] getProduct_ArrObj() {
		return (Product[]) product;
	}
	
	public void setProduct_item(Product product_item) {
		this.product_item = product_item;
	}
	
	public Product getOneProductItem() {
		return product_item;
	}
	
	public Product[] viewStoredCartItems() {
		/**
		 * @return This method tries to return all the Product array objects
		 * @note To view all the stored cart items pass an array of Products[] to the cart
		 */
		// Create an array list to be wrapped later as a Product[] object
		ArrayList<Product> product_arr_ls = new ArrayList<Product>();
		
		// Create a product object
		Product product_at_cart = new Product();
		
		System.out.println("Cart size = " + this.product.length);
		System.out.println("Trying to assign cart items for retrieval");
		for (int i = 0; i < this.product.length; i++) {			
			
			String cart_item_product_code = this.product[i].getProduct_code();
			String cart_item_product_name = this.product[i].getProduct_name();
			double cart_item_product_price = this.product[i].getProduct_price();
			
			
			/**
			 * @note Always instantiate the Product() so that it can always 
			 * have space for other attributes to be assigned from the cart
			 */
			product_at_cart = new Product(); 
			System.out.println("\n\nAssigning product code from cart ==> " + cart_item_product_code);
			product_at_cart.setProduct_code(cart_item_product_code);
			System.out.println("Assigning product name from cart ==> " + cart_item_product_name);
			product_at_cart.setProduct_name(cart_item_product_name);
			System.out.println("Assigning product price from cart ==> " + cart_item_product_price);
			product_at_cart.setProduct_price(cart_item_product_price);
			
			System.out.println("Adding to array list to be wrapped as in array object in return");
			product_arr_ls.add(product_at_cart);
			// return (Product[]) product_arr_ls.toArray(new Product[product_arr_ls.size()]);	
		}

		return (Product[]) product_arr_ls.toArray(new Product[product_arr_ls.size()]);
		
	}

	public String getProduct_code_at_cart() {
		return product_code_at_cart;
	}

	public void setProduct_code_at_cart(String product_code_at_cart) {
		this.product_code_at_cart = product_code_at_cart;
	}

	public String getProduct_name() {
		return product_name;
	}

	public void setProduct_name(String product_name) {
		this.product_name = product_name;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}
	
}
