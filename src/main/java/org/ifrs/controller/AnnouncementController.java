package org.ifrs.controller;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.eclipse.microprofile.jwt.JsonWebToken;

import org.ifrs.auth.TokenUtils;
import org.ifrs.entity.Error;
import org.ifrs.model.AnnouncementModel;
import org.ifrs.service.AnnouncementService;

@Path("announcement")
public class AnnouncementController {
    @Inject
    JsonWebToken token;

    @Inject
    AnnouncementService announcementService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response listAll() {
        try {
            return Response.ok(announcementService.getAllOpenned()).build();
        } catch (ClientErrorException e) {
            return new Error().toResponse(e);
        }
    }

    @GET
    @Path("logged")
    @RolesAllowed({ "User" })
    @Produces(MediaType.APPLICATION_JSON)
    public Response listAllLogged() {
        try {
            Long userId = TokenUtils.getUserId(token);

            return Response.ok(announcementService.getAllOpennedLogged(userId)).build();
        } catch (ClientErrorException e) {
            return new Error().toResponse(e);
        }
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getById(@PathParam("id") Long id) {
        try {
            return Response.ok(announcementService.getById(id)).build();
        } catch (ClientErrorException e) {
            return new Error().toResponse(e);
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    @RolesAllowed({ "User" })
    public Response create(@Valid AnnouncementModel announcement) {
        try {
            Long userId = TokenUtils.getUserId(token);

            return Response.ok(announcementService.create(announcement, userId)).build();
        } catch (ClientErrorException e) {
            return new Error().toResponse(e);
        }
    }

    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Transactional
    @RolesAllowed({ "User" })
    public Response update(@PathParam("id") Long id, @Valid AnnouncementModel announcement) {
        try {
            Long userId = TokenUtils.getUserId(token);

            announcementService.update(id, announcement, userId);

            return Response.ok().build();
        } catch (ClientErrorException e) {
            return new Error().toResponse(e);
        }
    }

    @DELETE
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Transactional
    @RolesAllowed({ "User" })
    public Response update(@PathParam("id") Long id) {
        try {
            announcementService.delete(id);

            return Response.ok().build();
        } catch (ClientErrorException e) {
            return new Error().toResponse(e);
        }
    }

    @GET
    @Path("user")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({ "User" })
    public Response getUserAnnouncements() {
        try {
            Long userId = TokenUtils.getUserId(token);

            return Response.ok(announcementService.getUserAnnouncements(userId)).build();
        } catch (ClientErrorException e) {
            return new Error().toResponse(e);
        }
    }

 
    @POST
    @Path("{id}/cancel")
    @RolesAllowed({ "User" })
    @Transactional
    public Response cancelAnnouncement(@PathParam("id") Long id) {
        try {
            announcementService.cancelAnnouncement(id);

            return Response.noContent().build();
        } catch (ClientErrorException e) {
            return new Error().toResponse(e);
        }
    }

    @POST
    @Path("{id}/adopt")
    @RolesAllowed({ "User" })
    @Transactional
    public Response finishAnnouncement(@PathParam("id") Long id) {
        try {
            announcementService.finishAnnouncement(id);

            return Response.noContent().build();
        } catch (ClientErrorException e) {
            return new Error().toResponse(e);
        }
    }
}
