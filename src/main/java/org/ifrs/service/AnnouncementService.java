package org.ifrs.service;

import java.util.List;

import javax.ws.rs.NotFoundException;

import org.ifrs.entity.Announcement;
import org.ifrs.entity.User;
import org.ifrs.enums.ErrorsEnum;
import org.ifrs.model.AnnouncementModel;

public class AnnouncementService {
    public List<Announcement> getAll() {
        return Announcement.listAll();
    }
     
    public Announcement getById(Long id) {
        return Announcement.findById(id);
    }

    public void update(Long id, AnnouncementModel announcement) {
        Announcement findedAnnouncement = Announcement.findById(id);

        if (findedAnnouncement == null) {
            throw new NotFoundException(ErrorsEnum.ANNOUNCEMENT_NOT_FOUND.getError());
        }

        findedAnnouncement.mapFromEntity(announcement);
        

        findedAnnouncement.persist();
    }

    public Announcement create(AnnouncementModel announcementModel) {
       User findedUser = User.findById(announcementModel.userId);

       if (findedUser == null) {
           throw new NotFoundException(ErrorsEnum.USER_NOT_FOUND.getError());
       }

       Announcement newAnnouncement = new Announcement();

       newAnnouncement.setOwner(findedUser);
       newAnnouncement.mapFromEntity(announcementModel);
       Announcement.persist(newAnnouncement);

       return newAnnouncement;
    }

    public void delete(Long id) {
        Announcement findedAnnouncement = Announcement.findById(id);

        if (findedAnnouncement == null) {
            throw new NotFoundException(ErrorsEnum.ANNOUNCEMENT_NOT_FOUND.getError());
        } 

        findedAnnouncement.delete();
    }

    public List<Announcement> getUserAnnouncements(Long userId) {
        User findedUser = User.findById(userId);

        if (findedUser == null) {
            throw new NotFoundException(ErrorsEnum.USER_NOT_FOUND.getError());
        }

        List<Announcement> announcements = Announcement.find("owner", findedUser).list();

        return announcements;
    }
}

