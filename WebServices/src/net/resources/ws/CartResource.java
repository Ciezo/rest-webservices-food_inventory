/*
    Document   : CartResource.java
    Package	   : WebServices.net.resource.ws;
    Created on : July 25, 2022
    Author     : Cloyd Van S. Secuya
    Description:
    	This is the Cart Resource that accesses the DAO files
*/

// PACKAGE
package net.resources.ws;


// IMPORT SECTION
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import net.resources.dao.CartDAO;


@Path("/checkout")
public class CartResource {

	private CartDAO dao = CartDAO.getInstance();
	
	@GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Cart> list() {
        return dao.listAll();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response add(Cart cart) throws URISyntaxException {
        int newProductId = dao.add(cart);
        URI uri = new URI("/WebServices/rest/checkout/" + newProductId);

        return Response.created(uri).build();
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response get(@PathParam("id") String id) {
        Cart cart = dao.get(id);

        if(cart != null ) {
            return Response.ok(cart, MediaType.APPLICATION_JSON).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
    
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("{id}")
    public Response update(@PathParam("id") String id, Cart cart) {
    	cart.setCart_ID(id);

        if(dao.update(cart)) {
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
