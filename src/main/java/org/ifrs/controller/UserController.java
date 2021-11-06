package org.ifrs.controller;

import java.util.List;

import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.ifrs.entity.User;
import org.ifrs.service.UserService;

@Path("/user")
public class UserController {
    UserService userService = new UserService();
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response listAll() {
        List<User> users = userService.getAll();
        
        return Response.ok(users).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public User create(User user) {
        return userService.create(user);
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Transactional
    public Response update(User user) {
        userService.update(user);

        return Response.noContent().build();
    }

}
