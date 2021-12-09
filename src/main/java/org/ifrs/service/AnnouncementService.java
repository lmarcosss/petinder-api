package org.ifrs.service;

import java.util.List;

import javax.ws.rs.NotFoundException;

import org.ifrs.entity.Announcement;
import org.ifrs.enums.AnnouncementStatusEnum;
import org.ifrs.enums.ErrorsEnum;
import org.ifrs.model.AnnouncementModel;

public class AnnouncementService {
    public List<Announcement> getAll() {
        return Announcement.listAll();
    }
     
    public Announcement getById(Long id) {
        Announcement findedAnnouncement = Announcement.findById(id);

        if (findedAnnouncement == null) {
            throw new NotFoundException(ErrorsEnum.ANNOUNCEMENT_NOT_FOUND.getError());
        } 

        return findedAnnouncement;
    }

    public void update(Long id, AnnouncementModel announcement) {
        Announcement findedAnnouncement = this.getById(id);

        findedAnnouncement.mapFromEntity(announcement);
        
        Announcement.persist(findedAnnouncement);
    }

    public Announcement create(AnnouncementModel announcementModel) {
    //    userService.getById(announcementModel.userId);

       Announcement newAnnouncement = new Announcement();
       
       newAnnouncement.mapFromEntity(announcementModel);
       
       Announcement.persist(newAnnouncement);

       return newAnnouncement;
    }

    public void delete(Long id) {
        Announcement findedAnnouncement = this.getById(id);

        findedAnnouncement.delete();
    }

    public List<Announcement> getUserAnnouncements(Long userId) {
        List<Announcement> announcements = Announcement.find("ownerId", userId).list();

        return announcements;
    }

    public void cancelAnnouncement(Long id) {
        Announcement findedAnnouncement = this.getById(id);

        findedAnnouncement.setClosed(true);
        findedAnnouncement.setStatus(AnnouncementStatusEnum.CANCELED.getStatus());

        Announcement.persist(findedAnnouncement);
    }

    public void finishAnnouncement(Long id) {
        Announcement findedAnnouncement = this.getById(id);

        findedAnnouncement.setClosed(true);
        findedAnnouncement.setStatus(AnnouncementStatusEnum.ADOPTED.getStatus());

        Announcement.persist(findedAnnouncement);
    }
}

