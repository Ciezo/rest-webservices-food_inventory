/*
    Document   : TestProductCreation.java
    Package	   : Database.src.resource;
    Created on : July 25, 2022
    Author     : Cloyd Van S. Secuya
    Description:
    	Here we can test multiple product creations where we also try to add this into the cart.
    	Then, from the cart, we try to retrieve the product object passed. 
    	We also try to get if the Cart is also properly working once it is instantiated with a product object 
    	as its parameter.
    	
    	Execute this class if you want to test making multiple products of Products[] array. 
    	Then, adding those into cart.
*/

// PACKAGE
package resource.local.test;


// IMPORT SECTION 
import resource.Cart;
import resource.Product;


public class TestProductCreation {
	
	public static void main(String[] args) {
		// Declare our cart object
		Cart cart;		
		
		System.out.println("Creation of product multiple products"); 
		Product[] product = new Product[] {
				new Product("Nitro 5", "Acer", 90000.00),
				new Product("Aspire One", "Acer", 20400.26),
				new Product("Swift", "Acer", 30000.54),
				new Product("Thinkpad", "Lenovo", 19000.00),
				new Product("Predator", "Acer", 100000.00)
		};
		System.out.println("Product created!"); 
		
		System.out.println("Displaying the product....");
		for (int j = 0; j < product.length; j++ ) {
			System.out.println("----------------------------------------------");
			System.out.println("Product Code: " + product[j].getProduct_code());
			System.out.println("Product Name: " + product[j].getProduct_name());
			System.out.println("Product Brand: " + product[j].getProduct_brand());
			System.out.println("Product Price: " + product[j].getProduct_price());
			System.out.println("----------------------------------------------");
		}
		
		try {Thread.sleep(5000);} catch (InterruptedException e) {e.printStackTrace();}
		
		System.out.println("\n\nAdding to cart");
		cart = new Cart(product);
		Product[] items_at_cart = cart.viewStoredCartItems();
		
		System.out.println("\n\nViewing Items at Cart....."); 
		for (int k = 0; k < items_at_cart.length; k++) {
			
			String cart_item_product_code = items_at_cart[k].getProduct_code();
			String cart_item_product_name = items_at_cart[k].getProduct_name();
			String cart_item_product_brand = items_at_cart[k].getProduct_brand();
			double cart_item_product_price = items_at_cart[k].getProduct_price();
			
			System.out.println("----------------------------------------------");
			System.out.println("Cart ID: " + cart.getCart_ID());
			System.out.println("\t ==> Cart Item [" + k + "]"); 
			System.out.println("\t\t ==> Product Code: " + cart_item_product_code); 
			System.out.println("\t\t ==> Product Name: " + cart_item_product_name); 
			System.out.println("\t\t ==> Product Brand: " + cart_item_product_brand); 
			System.out.println("\t\t ==> Product Price: " + cart_item_product_price); 
			System.out.println("----------------------------------------------");
		}
		
	}
	
}
