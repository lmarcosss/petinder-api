package org.ifrs.service;

import java.util.List;

import javax.ws.rs.NotFoundException;

import org.ifrs.entity.Announcement;
import org.ifrs.entity.User;

public class AnnouncementService {
    public List<Announcement> getAll() {
        return Announcement.listAll();
    }
     
    public Announcement getById(Integer announcementId) {
        return Announcement.findById(announcementId);
    }

    public void update(Announcement announcement) {
        Announcement findedAnnouncement = Announcement.findById(announcement.id);

        if (findedAnnouncement == null) {
            throw new NotFoundException("Anúncio não encontrado");
        }
        
        findUser(announcement.getOwner());

        findedAnnouncement.setDescription(announcement.getDescription());
        findedAnnouncement.setStatus(announcement.getStatus());
        findedAnnouncement.setIsClosed(announcement.getIsClosed());
        findedAnnouncement.setPictures(announcement.getPictures());

        findedAnnouncement.persist();
    }

    public Announcement create(Announcement announcement) {
        findUser(announcement.getOwner());

        announcement.persist();

        return announcement;
    }

    private void findUser(User user) {
        User findedUser = User.findById(user);

        if (findedUser == null) {
            throw new NotFoundException("Usuário não encontrado");
        } 
    }
}

