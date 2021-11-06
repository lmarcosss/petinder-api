package org.ifrs.controller;

import java.util.List;

import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.ifrs.entity.User;
import org.ifrs.service.UserService;

@Path("/user")
public class UserController {
    UserService userService = new UserService();
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<User> listAll() {
        return userService.getAll();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public User getById(@PathParam("id") Integer id) {
        return userService.getById(id);
    }

    @POST
    @Transactional
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public User create(User user) {
        return userService.create(user);
    }

    @PUT
    @Transactional
    @Consumes(MediaType.APPLICATION_JSON)
    public void update(User user) {
        userService.update(user);
    }

}
