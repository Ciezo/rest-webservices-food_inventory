/*
    Document   : UserDAO.java
    Package	   : WebServices.src.net.resources.ws;
    Created on : August 4, 2022
    Author     : Cloyd Van S. Secuya
    Description:
    	The User resource for WebContent
*/

// PACKAGE SECTION
package net.resources.dao;


// IMPORT SECTION
import java.util.List;
import net.resources.ws.User;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;


public class UserDAO {

	// Define a connection object from java.sql
	private Connection conn = null; 
	
	// We need to create an instance so we can fetch it with a resource
	private static UserDAO instance;
	// Create a static listing
	private static List<User> data = new ArrayList<>();
	// Finally, recreate another instance of a Product object to be assigned to the data as an Array List
	private static User user; 
	
	private UserDAO() {
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
	
	public static UserDAO getInstance() {
		if(instance == null) {
			instance = new UserDAO(); 
		}
		return instance;
	}
	
	public List<User> listAll() {
		data.clear();
        // SQL Statement
		String SQL = "SELECT * FROM User_tbl";

        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(SQL);

            while(rs.next()) {
                User user = new User();
                user.set_User_ID(rs.getString("User_ID"));
                user.set_UserName(rs.getString("User_name"));
                user.set_UserEmail(rs.getString("Email"));
                user.set_UserPassword(rs.getString("User_pswd"));
                user.setFirst_name(rs.getString("First_name"));
                user.setLast_name(rs.getString("Last_name"));
                                
                data.add(user);
            }
        } 
        
        catch (Exception e) {
        	System.out.println(e);
        }
        
        return new ArrayList<User>(data);
    }
	
	public int add(User user) {
		// SQL statement
		// INSERT INTO User_tbl(User_ID, User_name, Email, User_pswd, First_name, Last_name)
		String SQL = "INSERT INTO User_tbl(User_ID, User_name, Email, User_pswd, First_name, Last_name) VALUES(?, ?, ?, ?, ?, ?)";
		 
		 int newItemCode = data.size() + 1;
		 
		 try {	
			 PreparedStatement st = conn.prepareStatement(SQL);
			 // st.setInt(1, newItemCode);
			 st.setString(1, user.get_User_ID());
			 st.setString(2, user.get_UserName());
			 st.setString(3, user.get_UserEmail());
			 st.setString(4, user.get_UserPassword());
			 st.setString(5, user.getFirst_name());
			 st.setString(6, user.getLast_name());
			 
			 st.executeUpdate();
			 
			 data.add(user);
			 
		 } 
		 
		 catch(Exception e) {
			 System.out.println(e);
	     }
		 
		 return newItemCode;
	}
	
	public User get(String id) {
		// SQL Statement 
		String SQL = "SELECT * FROM User_tbl WHERE User_ID = " + "'"+id+"'"; 
		
		User userToFind = new User(); 
		
		try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(SQL);
            while(rs.next()) {
            	userToFind.set_User_ID(rs.getString("User_ID"));
            	userToFind.set_UserName(rs.getString("User_name"));
            	userToFind.set_UserEmail(rs.getString("Email"));
            	userToFind.set_UserPassword(rs.getString("User_pswd"));
            	userToFind.setFirst_name(rs.getString("First_name"));
            	userToFind.setLast_name(rs.getString("Last_name"));
            }

        }
        
        catch (Exception e) {
        	System.out.println(e);
        }

		
		return userToFind; 
	}
	
	public boolean delete(String id) {
		// SQL Statement
		String SQL = "DELETE FROM User_tbl WHERE User_ID=?";
		
		User userToFind = new User(); 
        
		try {
            PreparedStatement st = conn.prepareStatement(SQL);
            st.setString(1, id);
            st.executeUpdate();

        }
        
        catch(Exception e) {
        	System.out.println(e);
        }

        int index = data.indexOf(userToFind);

        if(index >= 0) {
        	data.remove(index);
        	return true;
        }

        return false;
	}
	
	public boolean update(User user) {
		// SQL Statement
		String SQL = "UPDATE User_tbl SET User_name=?, Email=?, User_pswd=?, First_name=?, Last_name=?, where User_ID=?";
		
		try {
			PreparedStatement st = conn.prepareStatement(SQL);
	        st.setString(1, user.get_User_ID());
	        st.setString(2, user.get_UserName());
	        st.setString(3, user.get_UserEmail());
	        st.setString(4, user.get_UserPassword());
	        st.setString(5, user.getFirst_name());
	        st.setString(6, user.getLast_name());
	        st.executeUpdate();
	    }
	    
	    catch(Exception e) {
	    	e.printStackTrace();
	    }
	    
	    int index = data.indexOf(user);
	    
	    if(index >= 0) {
	    	data.set(index, user);
	    	return true;
	     }
	    
	    return false;
	}
	
}
