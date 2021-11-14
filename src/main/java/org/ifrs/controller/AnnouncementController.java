package org.ifrs.controller;

import java.util.List;

import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.ifrs.entity.Announcement;
import org.ifrs.entity.Interest;
import org.ifrs.model.AnnouncementModel;
import org.ifrs.service.AnnouncementService;

@Path("announcement")
public class AnnouncementController {
    AnnouncementService announcementService = new AnnouncementService();
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Announcement> listAll() {
        return announcementService.getAll();
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Announcement getById(@PathParam("id") Long id) {
        return announcementService.getById(id);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public Announcement create(AnnouncementModel announcement) {
        return announcementService.create(announcement);
    }

    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Transactional
    public void update(@PathParam("id") Long id, AnnouncementModel announcement) {
        announcementService.update(id, announcement);
    }

    @DELETE
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Transactional
    public void update(@PathParam("id") Long id) {
        announcementService.delete(id);
    }

    @GET
    @Path("user/{userId}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Announcement> getUserAnnouncements(@PathParam("userId") Long userId) {
        return announcementService.getUserAnnouncements(userId);
    }
}
