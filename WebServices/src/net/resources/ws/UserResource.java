/*
    Document   : UserResource.java
    Package	   : WebServices.src.net.resources.ws;
    Created on : August 4, 2022
    Author     : Cloyd Van S. Secuya
    Description:
    	The User resource for WebContent
*/

// PACKAGE SECTION
package net.resources.ws;


// IMPORT SECTION 
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import net.resources.dao.UserDAO;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;


@Path("/users")
public class UserResource {

	private UserDAO dao = UserDAO.getInstance(); 
	
	@GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<User> list() {
        return dao.listAll();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response add(User user) throws URISyntaxException {
        int newProductId = dao.add(user);
        URI uri = new URI("/WebServices/rest/users/" + newProductId);

        return Response.created(uri).build();
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response get(@PathParam("id") String id) {
        User user = dao.get(id);

        if(user != null ) {
            return Response.ok(user, MediaType.APPLICATION_JSON).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
    
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("{id}")
    public Response update(@PathParam("id") String id, User user) {
        user.set_User_ID(id);

        if(dao.update(user)) {
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
