package org.ifrs.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.ws.rs.NotFoundException;

import org.eclipse.microprofile.rest.client.inject.RestClient;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.ifrs.adapter.AnnouncementAdapter;
import org.ifrs.client.UserClient;
import org.ifrs.entity.Announcement;
import org.ifrs.enums.AnnouncementStatusEnum;
import org.ifrs.enums.ErrorsEnum;
import org.ifrs.model.AnnouncementModel;
import org.ifrs.view.AnnouncementView;
import org.ifrs.view.UserView;

@Singleton
public class AnnouncementService {
    @Inject
    @RestClient
    UserClient userService;

    private AnnouncementView formatAnnouncement(Announcement announcement) {
        UserView owner = userService.getById(announcement.getOwnerId());

        AnnouncementAdapter adapter = new AnnouncementAdapter(announcement, owner);
        
        return adapter.mapEntityToView();
    }

    private List<AnnouncementView> formatAnnouncements(List<Announcement> announcements) {
        return announcements.stream().map(announcement -> formatAnnouncement(announcement)).collect(Collectors.toList());
    }
    
    public List<AnnouncementView> getAll() {
        List<Announcement> announcements = Announcement.listAll();
        
        return formatAnnouncements(announcements);
    }
     
    public AnnouncementView getById(Long id) {
        Announcement findedAnnouncement = Announcement.findById(id);

        if (findedAnnouncement == null) {
            throw new NotFoundException(ErrorsEnum.ANNOUNCEMENT_NOT_FOUND.getError());
        }

        UserView owner = userService.getById(findedAnnouncement.getOwnerId());

        if (owner == null) {
            throw new NotFoundException(ErrorsEnum.USER_NOT_FOUND.getError());
        }

        AnnouncementAdapter announcement = new AnnouncementAdapter(findedAnnouncement, owner);

        return announcement.mapEntityToView();
    }

    public void update(Long id, AnnouncementModel announcement) {
        Announcement findedAnnouncement = Announcement.findById(id);

        if (findedAnnouncement == null) {
            throw new NotFoundException(ErrorsEnum.ANNOUNCEMENT_NOT_FOUND.getError());
        }

        AnnouncementAdapter adapter = new AnnouncementAdapter(findedAnnouncement);

        adapter.mapModelToEntity(announcement);

        Announcement.persist(adapter.getAnnouncement());
    }

    public AnnouncementView create(AnnouncementModel announcementModel) {
        UserView owner = userService.getById(announcementModel.userId);

        if (owner == null) {
            throw new NotFoundException(ErrorsEnum.USER_NOT_FOUND.getError());
        }

        Announcement newAnnouncement = new Announcement();

        AnnouncementAdapter adapter = new AnnouncementAdapter(newAnnouncement);

        adapter.mapModelToEntity(announcementModel);
        
        Announcement.persist(adapter.getAnnouncement());

        return adapter.mapEntityToView();
    }

    public void delete(Long id) {
        Announcement findedAnnouncement = Announcement.findById(id);

        if (findedAnnouncement == null) {
            throw new NotFoundException(ErrorsEnum.ANNOUNCEMENT_NOT_FOUND.getError());
        }

        findedAnnouncement.delete();
    }

    public List<AnnouncementView> getUserAnnouncements(Long userId) {
        List<Announcement> announcements = Announcement.find("ownerId", userId).list();

        return formatAnnouncements(announcements);
    }

    public void cancelAnnouncement(Long id) {
        Announcement findedAnnouncement = Announcement.findById(id);

        if (findedAnnouncement == null) {
            throw new NotFoundException(ErrorsEnum.ANNOUNCEMENT_NOT_FOUND.getError());
        }

        findedAnnouncement.setClosed(true);
        findedAnnouncement.setStatus(AnnouncementStatusEnum.CANCELED.getStatus());

        Announcement.persist(findedAnnouncement);
    }

    public void finishAnnouncement(Long id) {
        Announcement findedAnnouncement = Announcement.findById(id);

        if (findedAnnouncement == null) {
            throw new NotFoundException(ErrorsEnum.ANNOUNCEMENT_NOT_FOUND.getError());
        }

        findedAnnouncement.setClosed(true);
        findedAnnouncement.setStatus(AnnouncementStatusEnum.ADOPTED.getStatus());

        Announcement.persist(findedAnnouncement);
    }
}

