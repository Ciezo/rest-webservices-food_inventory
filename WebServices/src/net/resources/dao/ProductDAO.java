/*
    Document   : ProductDAO.java
    Package	   : WebServices.src.net.resources.dao;
    Created on : August 4, 2022
    Author     : Cloyd Van S. Secuya
    Description:
    	This is where we try to create DAO files with the help of our custom
    	Proxy.Driver.java where we try to directly pull from the remote Heroku Database
    	then, access them to put into a RESTful state with their identified URL.
    	Make the resources as a DAO. 
*/

// PACKAGE SECTION
package net.resources.dao;


// IMPORT SECTION
//import com.db.driver.Driver;
//import resource.Product;
import java.util.List;
import net.resources.ws.Product;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;


public class ProductDAO {
	
	// Define a connection object from java.sql
	private Connection conn = null; 
		
	// We need the Driver and its method to directly pull contents from the database
	// private static Driver driver = new Driver(); 
	// We need to create an instance so we can fetch it with a resource
	private static ProductDAO instance;
	// Create a static listing
	private static List<Product> data = new ArrayList<>();
	// Fetch the inventory contents of the database, Product_tbl and assign it here
	private static resource.Product[] product_obj_arr; 
	// Finally, recreate another instance of a Product object to be assigned to the data as an Array List
	private static Product product; 
	
	private ProductDAO() {
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
	
	public static ProductDAO getInstance() {
		if(instance == null) {
			instance = new ProductDAO();
		}
		return instance;
	}
	
	public List<Product> listAll() {
		data.clear();
        // SQL Statement
		String SQL = "SELECT * FROM Product_tbl";

        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(SQL);

            while(rs.next()) {
                Product p = new Product();
                 p.setProduct_code(rs.getString("Product_code"));
                 p.setProduct_name(rs.getString("Product_name"));
                 p.setProduct_price(rs.getDouble("Price"));
                                
                data.add(p);
            }
        } 
        
        catch (Exception e) {
        	System.out.println(e);
        }
        
        return new ArrayList<Product>(data);
    }
	
	public int add(Product product) {
		// SQL statement
		String SQL = "INSERT INTO Product_tbl(Product_code, Product_name, Price) VALUES(?, ?, ?)";
		 
		 int newItemCode = data.size() + 1;
		 
		 try {	
			 PreparedStatement st = conn.prepareStatement(SQL);
			 // st.setInt(1, newItemCode);
			 st.setString(1, product.getProduct_code()); 
			 st.setString(2, product.getProduct_name());
			 st.setDouble(3, product.getProduct_price());
			 st.executeUpdate();
			 
			 data.add(product);
			 
		 } 
		 
		 catch(Exception e) {
			 System.out.println(e);
	     }
		 
		 return newItemCode;
	}
	
	public Product get(String id) {
		// SQL Statement 
		String SQL = "SELECT * FROM Product_tbl WHERE Product_code = " + "'"+id+"'"; 

        Product productToFind = new Product(id);

        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(SQL);
            while(rs.next()) {
                productToFind.setProduct_code(rs.getString("Product_code"));
                productToFind.setProduct_name(rs.getString("Product_name"));
                productToFind.setProduct_price(rs.getDouble("Price"));
            }

        }
        
        catch (Exception e) {
        	System.out.println(e);
        }

        return productToFind;
	}
	
	public boolean delete(String id) {
		// SQL Statement
		String SQL = "DELETE FROM Product_tbl WHERE Product_code=?";
		
		Product productToFind = new Product(id);
        
		try {
            PreparedStatement st = conn.prepareStatement(SQL);
            st.setString(1, id);
            st.executeUpdate();

        }
        
        catch(Exception e) {
        	System.out.println(e);
        }

        int index = data.indexOf(productToFind);

        if(index >= 0) {
        	data.remove(index);
        	return true;
        }

        return false;
	}
	
	public boolean update(Product product) {
		// SQL Statement
		String SQL = "UPDATE Product_tbl SET Product_name=?, Price=?, where Product_code=?";
		
		try {
			PreparedStatement st = conn.prepareStatement(SQL);
	        st.setString(1, product.getProduct_code());
	        st.setString(2, product.getProduct_name());
	        st.setDouble(3, product.getProduct_price());
	        st.executeUpdate();
	    }
	    
	    catch(Exception e) {
	    	e.printStackTrace();
	    }
	    
	    int index = data.indexOf(product);
	    
	    if(index >= 0) {
	    	data.set(index, product);
	    	return true;
	     }
	    
	    return false;
	}
}
