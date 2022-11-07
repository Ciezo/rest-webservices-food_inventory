/*
    Document   : ProductResource.java
    Package	   : WebServices.net.resource.ws;
    Created on : August 4, 2022
    Author     : Cloyd Van S. Secuya
    Description:
    	We try to wrap the methods of the Proxy.Driver with a RESTful service.
*/

// PACKAGE SECTION
package net.resources.ws;


// IMPORT SECTION
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import net.resources.dao.ProductDAO;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import java.sql.*;
//import resource.Product;
//import com.db.driver.Driver;


@Path("/products")
public class ProductResource {

	private ProductDAO dao = ProductDAO.getInstance();
//	Driver driver = new Driver(); 
	
	@GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Product> list() {
        return dao.listAll();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response add(Product product) throws URISyntaxException {
        int newProductId = dao.add(product);
        URI uri = new URI("/WebServices/rest/products/" + newProductId);

        return Response.created(uri).build();
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response get(@PathParam("id") String id) {
        Product product = dao.get(id);

        if(product != null ) {
            return Response.ok(product, MediaType.APPLICATION_JSON).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
    
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("{id}")
    public Response update(@PathParam("id") String id, Product product) {
        product.setProduct_code(id);

        if(dao.update(product)) {
            return Response.ok().build();
        } else {
            return Response.notModified().build();
        }
    }

    @DELETE
    @Path("{id}")
    public Response delete(@PathParam("id") String id) {
        if(dao.delete(id)) {
            return Response.ok().build();
        } else {
            return Response.notModified().build();
        }
    }
	
}
