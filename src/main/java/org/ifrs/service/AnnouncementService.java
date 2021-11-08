package org.ifrs.service;

import java.util.List;

import javax.ws.rs.NotFoundException;

import org.ifrs.entity.Announcement;
import org.ifrs.enums.ErrorsEnum;

public class AnnouncementService {
    public List<Announcement> getAll() {
        return Announcement.listAll();
    }
     
    public Announcement getById(Long id) {
        return Announcement.findById(id);
    }

    public void update(Long id, Announcement announcement) {
        Announcement findedAnnouncement = Announcement.findById(id);

        if (findedAnnouncement == null) {
            throw new NotFoundException(ErrorsEnum.ANNOUNCEMENT_NOT_FOUND.getError());
        }
        
        findedAnnouncement.setDescription(announcement.getDescription());
        findedAnnouncement.setStatus(announcement.getStatus());
        findedAnnouncement.setIsClosed(announcement.getIsClosed());
        findedAnnouncement.setPictures(announcement.getPictures());

        findedAnnouncement.persist();
    }

    public Announcement create(Announcement announcement) {
        announcement.persist();

        return announcement;
    }

    public void delete(Long id) {
        Announcement findedAnnouncement = Announcement.findById(id);

        if (findedAnnouncement == null) {
            throw new NotFoundException(ErrorsEnum.ANNOUNCEMENT_NOT_FOUND.getError());
        } 

        findedAnnouncement.delete();
    }
}

