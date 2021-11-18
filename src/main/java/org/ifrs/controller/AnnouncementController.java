package org.ifrs.controller;

import javax.transaction.Transactional;
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

import org.ifrs.entity.Error;
import org.ifrs.model.AnnouncementModel;
import org.ifrs.service.AnnouncementService;

@Path("announcement")
public class AnnouncementController {
    AnnouncementService announcementService = new AnnouncementService();
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response listAll() {
        try {
            return Response.ok(announcementService.getAll()).build();
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
    public Response create(AnnouncementModel announcement) {
        try {
            return Response.ok(announcementService.create(announcement)).build();
        } catch (ClientErrorException e) {
            return new Error().toResponse(e);
        }
    }

    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Transactional
    public Response update(@PathParam("id") Long id, AnnouncementModel announcement) {
        try {
            announcementService.update(id, announcement);

            return Response.ok().build();
        } catch (ClientErrorException e) {
            return new Error().toResponse(e);
        }
    }

    @DELETE
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Transactional
    public Response update(@PathParam("id") Long id) {
        try {
            announcementService.delete(id);

            return Response.ok().build();
        } catch (ClientErrorException e) {
            return new Error().toResponse(e);
        }
    }

    @GET
    @Path("user/{userId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUserAnnouncements(@PathParam("userId") Long userId) {
        try {
            return Response.ok(announcementService.getUserAnnouncements(userId)).build();
        } catch (ClientErrorException e) {
            return new Error().toResponse(e);
        }
    }

    @POST
    @Path("{id}/cancel")
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
