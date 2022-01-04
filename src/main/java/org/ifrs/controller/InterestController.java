package org.ifrs.controller;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.eclipse.microprofile.jwt.JsonWebToken;
import org.ifrs.auth.TokenUtils;
import org.ifrs.entity.Error;
import org.ifrs.model.InterestModel;
import org.ifrs.service.InterestService;

@Path("interest")
public class InterestController {
    @Inject
    JsonWebToken token;

    @Inject
    InterestService interestService;
    
    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getById(@PathParam("id") Long id) {
        try {
            return Response.ok(interestService.getById(id)).build();
            
        } catch (ClientErrorException e) {
            return new Error().toResponse(e);
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({ "User" })
    @Transactional
    public Response create(@Valid InterestModel interest) {
        try {
            Long loggedUserId = TokenUtils.getUserId(token);
            interest.interestedId = loggedUserId;

            return Response.ok(interestService.create(interest)).build();
        } catch (ClientErrorException e) {
            return new Error().toResponse(e);
        }
    }

    @GET
    @Path("announcement/{announcementId}")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({ "User" })
    public Response getInterestsByAnnouncement(@PathParam("announcementId") Long announcementId) {
        try {
            return Response.ok(interestService.getInterestsByAnnouncement(announcementId)).build();
            
        } catch (ClientErrorException e) {
            return new Error().toResponse(e);
        }
    }

    
    @GET
    @Path("user/{userId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUserInterestAnnouncements(@PathParam("userId") Long userId) {
        try {
            return Response.ok(interestService.getUserInterests(userId)).build();
        } catch (ClientErrorException e) {
            return new Error().toResponse(e);
        }
    }

    @POST
    @Path("{id}/decline")
    @RolesAllowed({ "User" })
    @Transactional
    public Response declineInterestByAnnoucement(@PathParam("id") Long id) {
        try {
            interestService.declineInterestByAnnoucement(id);
            
            return Response.ok().build();
            
        } catch (ClientErrorException e) {
            return new Error().toResponse(e);
        }
    }

    @POST
    @Path("{id}/accept")
    @RolesAllowed({ "User" })
    @Transactional
    public Response acceptInterestByAnnoucement(@PathParam("id") Long id) {
        try {
            interestService.acceptInterestByAnnoucement(id);
            
            return Response.ok().build();
            
        } catch (ClientErrorException e) {
            return new Error().toResponse(e);
        }
    }
}