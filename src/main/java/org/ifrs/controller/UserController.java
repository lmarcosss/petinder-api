package org.ifrs.controller;

import java.util.List;
import javax.ws.rs.GET;
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
    public Response getUsers() {
        List<User> users = userService.getAll();
        
        return Response.ok(users).build();
    }

}
