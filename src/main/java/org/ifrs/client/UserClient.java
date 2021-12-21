package org.ifrs.client;

import javax.ws.rs.Produces;
import javax.annotation.security.PermitAll;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import org.ifrs.view.UserView;

@RegisterRestClient(configKey="user-api")
public interface UserClient {

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @PermitAll
    public UserView getById(@PathParam("id") Long id);
}
