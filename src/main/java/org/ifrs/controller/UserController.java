package org.ifrs.controller;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.ifrs.entity.User;

@Path("/user")
public class UserController {
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUser() {
        User user = new User();
        user.setId(Long.parseLong("143423423"));
        user.setName("Chico, o brabo");
        User[] users = { user };

        return Response.ok(users).build();
    }
}
