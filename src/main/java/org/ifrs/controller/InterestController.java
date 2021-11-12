package org.ifrs.controller;

import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.ifrs.entity.Interest;
import org.ifrs.model.InterestModel;
import org.ifrs.service.InterestService;

@Path("/interest")
public class InterestController {
    InterestService interestService = new InterestService();
    
    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Interest getById(@PathParam("id") Long id) {
        return interestService.getById(id);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public Interest create(InterestModel interest) {
        return interestService.create(interest);
    }
}