/*
    Document   : ClientConsumer.java
    Package	   : WebServices.src.net.resources.dao;
    Created on : August 5, 2022
    Author     : Cloyd Van S. Secuya
    Description:
    	A class file with a static method to view resources on console or terminal
*/

// PACKAGE SECTION
package client.build.consumer;


// IMPORT SECTION
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import org.glassfish.jersey.client.ClientConfig;
import java.util.Scanner;

public class ClientConsumer {

	static Scanner sc = new Scanner(System.in); 
	
	public static void main(String[] args) {

		String URI = "";
		ClientConfig config;
		Client client; 
		WebTarget target; 
		String response = ""; 
		
		while (true) {
			System.out.println("\n\n");
			System.out.println("PRODUCTS PAGE   [1]");
			System.out.println("USER PAGE       [2]");
			System.out.println("CHECKOUT       	[3]");
			System.out.print(">>>> ");
			int opt = sc.nextInt();
			
			switch(opt) {
				case 1:
					URI = "http://localhost:8080/WebServices/rest/products";
					config = new ClientConfig(); 
					client = ClientBuilder.newClient(config);
					target = client.target(URI); 
					
					response = target.request()
									 .accept(MediaType.APPLICATION_JSON)
									 .get(String.class); 
					
					System.out.println(">>> VIEWING ENTIRE PRODUCTS RESOURCES CONTENT <<<");
					System.out.println(response);
					break; 
					
				case 2: 
					URI = "http://localhost:8080/WebServices/rest/users";
					config = new ClientConfig(); 
					client = ClientBuilder.newClient(config);
					target = client.target(URI); 
					
					response = target.request()
									 .accept(MediaType.APPLICATION_JSON)
									 .get(String.class); 
					
					System.out.println(">>> VIEWING ENTIRE USER RESOURCES CONTENT <<<");
					System.out.println(response);
					break; 
					
				case 3: 
					URI = "http://localhost:8080/WebServices/rest/checkout";
					config = new ClientConfig(); 
					client = ClientBuilder.newClient(config);
					target = client.target(URI); 
					
					response = target.request()
									 .accept(MediaType.APPLICATION_JSON)
									 .get(String.class); 
					
					System.out.println(">>> VIEWING ENTIRE CHECKOUT RESOURCES CONTENT <<<");
					System.out.println(response);
					break;
					
				default:
					break;
			}
		}	
	}
}
