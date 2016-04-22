package com.fdvsolutions.brigada.rest.service;
 
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;

import com.fdvsolutions.brigada.rest.dao.UserDao;
import com.fdvsolutions.brigada.rest.model.User;
 
@Path("/user-service")
public class UserService {

	@PermitAll
    @GET
    @Path("/users/{id}")
    public Response getUserById(@PathParam("id") int id, @Context Request req) {
		
		User anUser = UserDao.getUserById(id);
        Response.ResponseBuilder rb = Response.ok(anUser.toString());
        
        return rb.build();
    }
     
	@RolesAllowed("ADMIN")
    @GET
    @Path("/userscount")
    public Response getUsersCountAuth(@Context Request req) {
		
        Response.ResponseBuilder rb = Response.ok(UserDao.getAllUsers().size());
        return rb.build();
    }

}
