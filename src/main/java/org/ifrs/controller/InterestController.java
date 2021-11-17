package org.ifrs.controller;

import java.util.List;

import javax.transaction.Transactional;
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.ifrs.entity.Error;
import org.ifrs.entity.Interest;
import org.ifrs.model.InterestModel;
import org.ifrs.service.InterestService;

@Path("interest")
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
    public Response create(InterestModel interest) {
        try {
            return Response.ok(interestService.create(interest)).build();
        } catch (ClientErrorException e) {
            return new Error().toResponse(e);
        }
    }

    @GET
    @Path("announcement/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getInterestsByAnnouncement(@PathParam("id") Long id) {
        try {
            return Response.ok(interestService.getInterestsByAnnouncement(id)).build();
            
        } catch (ClientErrorException e) {
            return new Error().toResponse(e);
        }
    }
}