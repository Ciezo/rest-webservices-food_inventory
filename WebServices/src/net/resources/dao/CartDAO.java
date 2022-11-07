/*
    Document   : CartDAO.java
    Package	   : WebServices.src.net.resources.dao;
    Created on : August 5, 2022
    Author     : Cloyd Van S. Secuya
    Description:
    	We try to wrap the methods of the Proxy.Driver with a RESTful service.
*/

// PACKAGE SECTION
package net.resources.dao;

// IMPORT SECTION
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import net.resources.ws.Cart;
import net.resources.ws.Product;

public class CartDAO {
	
	// Define a connection object from java.sql
	private Connection conn = null; 
	// We need to create an instance so we can fetch it with a resource
	private static CartDAO instance; 
	// Create a static listing
	private static List<Cart> data = new ArrayList<>();
	// Finally, recreate another instance of a Product object to be assigned to the data as an Array List
	private static Cart cart; 
	
	private CartDAO() {
		String URL = "jdbc:postgresql://ec2-18-214-35-70.compute-1.amazonaws.com:5432/d5oktcpujqqcse"; 
		String username = "lemzyextdjbmcj"; 
		String password = "8da3716509fdd2d9e072ec173129a64342f9539bb1e8260f58a07d91bf135c07";
		try {
			// Driver set-up and initialization 
			DriverManager.registerDriver(new org.postgresql.Driver());
			// Establish the connection
			conn = DriverManager.getConnection(URL, username, password);
		}
		
		catch (SQLException e) {
			System.out.println("An error occured!");
			e.printStackTrace();
			System.out.println(e.getMessage()); 
		}
		
	}
	
	public static CartDAO getInstance() {
		if(instance == null) {
			instance = new CartDAO();
		}
		return instance;
	}
	
	public List<Cart> listAll() {
		data.clear();
        // SQL Statement
		String SQL = "SELECT * FROM Cart_tbl";

        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(SQL);

            while(rs.next()) {
            	Cart cart = new Cart();
            	cart.setCart_ID(rs.getString("Cart_ID"));
            	cart.setProduct_code_at_cart(rs.getString("Product_code_at_cart"));
            	cart.setProduct_name(rs.getString("Product_name"));
            	cart.setPrice(rs.getDouble("Price"));
            	
            	data.add(cart);
            }
        } 
        
        catch (Exception e) {
        	System.out.println(e);
        }
        
        return new ArrayList<Cart>(data);
    }
	
	public int add(Cart cart) {
		// SQL statement
		String SQL = "INSERT INTO Cart_tbl(Cart_ID, Product_code_at_cart, Product_name, Price) VALUES(?, ?, ?, ?)";
		
		int newItemCode = data.size() + 1;
		
		try {	
			PreparedStatement st = conn.prepareStatement(SQL);
			// st.setInt(1, newItemCode);
			st.setString(1, cart.getCart_ID()); 
			st.setString(2, cart.getProduct_code_at_cart());
			st.setString(3, cart.getProduct_name());
			st.setDouble(4, cart.getPrice());
			st.executeUpdate();
			
			data.add(cart); 
		 } 
		 
		 catch(Exception e) {
			 System.out.println("1");
			 System.out.println(e);
	     }
		 
		 return newItemCode;
	}
	
	public Cart get(String id) {
		// SQL Statement 
		String SQL = "SELECT * FROM Cart_tbl WHERE Cart_ID = " + "'"+id+"'"; 

        Cart cartToFind = new Cart();

        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(SQL);
            while(rs.next()) {
            	cartToFind.setCart_ID(rs.getString("Cart_ID"));
            	cartToFind.setProduct_code_at_cart(rs.getString("Product_code_at_cart"));
            	cartToFind.setProduct_name(rs.getString("Product_name"));
            	cartToFind.setPrice(rs.getDouble("Price"));
            }

        }
        
        catch (Exception e) {
        	System.out.println("2");
        	System.out.println(e);
        }

        return cartToFind;
	}
	
	public boolean delete(String id) {
		// SQL Statement
		String SQL = "DELETE FROM Cart_tbl WHERE Cart_ID=?";
		
		Cart cartToFind = new Cart();
        
		try {
            PreparedStatement st = conn.prepareStatement(SQL);
            st.setString(1, id);
            st.executeUpdate();

        }
        
        catch(Exception e) {
        	System.out.println(e);
        }

        int index = data.indexOf(cartToFind);

        if(index >= 0) {
        	data.remove(index);
        	return true;
        }

        return false;
	}
	
	public boolean update(Cart cart) {
		// SQL Statement
		String SQL = "UPDATE Cart_tbl SET Product_code_at_cart=?, Product_name=?, Price=?, where Cart_ID=?";
		
		try {
			PreparedStatement st = conn.prepareStatement(SQL);
	        st.setString(1, cart.getCart_ID());
	        st.setString(2, cart.getProduct_code_at_cart());
	        st.setString(3, cart.getProduct_name());
	        st.setDouble(4, cart.getPrice());
	        st.executeUpdate();
	    }
	    
	    catch(Exception e) {
	    	e.printStackTrace();
	    }
	    
	    int index = data.indexOf(cart);
	    
	    if(index >= 0) {
	    	data.set(index, cart);
	    	return true;
	     }
	    
	    return false;
	}

}
