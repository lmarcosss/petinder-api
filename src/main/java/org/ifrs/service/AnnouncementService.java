package org.ifrs.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.ws.rs.NotFoundException;

import org.eclipse.microprofile.config.ConfigProvider;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.stream.Collectors;

import org.ifrs.adapter.AnnouncementAdapter;
import org.ifrs.client.GeolocationClient;
import org.ifrs.client.UserClient;
import org.ifrs.entity.Announcement;
import org.ifrs.entity.Interest;
import org.ifrs.enums.AnnouncementStatusEnum;
import org.ifrs.enums.ErrorsEnum;
import org.ifrs.model.AnnouncementModel;
import org.ifrs.model.GeolocationModel;
import org.ifrs.view.AnnouncementView;
import org.ifrs.view.UserView;

import io.quarkus.panache.common.Sort;

@Singleton
public class AnnouncementService {
    @Inject
    @RestClient
    UserClient userService;

    @Inject
    @RestClient
    GeolocationClient geolocationService;

    private GeolocationModel getGeolocation(double lat, double lon) {
        String apiKey = ConfigProvider.getConfig().getValue("petinder.locationiq-api-key", String.class);

        return geolocationService.getGeolocation(apiKey, lat, lon, "pt-br", 10, "json");
    }

    private AnnouncementView formatAnnouncement(Announcement announcement) {
        UserView owner = userService.getById(announcement.getOwnerId());

        AnnouncementAdapter adapter = new AnnouncementAdapter(announcement, owner);
        
        return adapter.mapEntityToView(null);
    }

    private List<AnnouncementView> formatAnnouncements(List<Announcement> announcements) {
        return announcements.stream().map(announcement -> formatAnnouncement(announcement)).collect(Collectors.toList());
    }
    
    public List<AnnouncementView> getAllOpenned() {
        List<Announcement> announcements = Announcement.find(
            "status",
            Sort.by("id").descending(),
            AnnouncementStatusEnum.OPENNED.getStatus()
        ).list();
        
        return formatAnnouncements(announcements);
    }

    public AnnouncementView formatAnnouncement(Announcement announcement, Long userId) {
       Interest interest = Interest.find(
           "interestedId = ?1 and announcementId = ?2",
           userId,  
           announcement.getId()
        ).firstResult();

        String interestStatus = interest != null ? interest.getStatus() : null;

        UserView owner = userService.getById(userId);

        AnnouncementAdapter adapter = new AnnouncementAdapter(announcement, owner);

        return adapter.mapEntityToView(interestStatus);
    }

    public List<AnnouncementView> getAllOpennedLogged(Long userId) {

        List<Announcement> announcements = Announcement.find(
            "status",
            Sort.by("id").descending(),
            AnnouncementStatusEnum.OPENNED.getStatus()
        ).list();

        List<AnnouncementView> filteredAnnouncements = announcements.stream().map(
            announcement -> formatAnnouncement(announcement, userId)
        ).collect(Collectors.toList()); 
        
        return filteredAnnouncements;
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

        return announcement.mapEntityToView(null);
    }

    public void update(Long id, AnnouncementModel announcementModel, Long userId) {
        Announcement findedAnnouncement = Announcement.findById(id);

        if (findedAnnouncement == null) {
            throw new NotFoundException(ErrorsEnum.ANNOUNCEMENT_NOT_FOUND.getError());
        }

        UserView owner = userService.getById(userId);

        if (owner == null) {
            throw new NotFoundException(ErrorsEnum.USER_NOT_FOUND.getError());
        }

        GeolocationModel geolocationModel = getGeolocation(announcementModel.latitude, announcementModel.longitude);

        AnnouncementAdapter adapter = new AnnouncementAdapter(findedAnnouncement, owner);

        adapter.mapModelToEntity(announcementModel, geolocationModel, userId);

        Announcement.persist(adapter.getAnnouncement());
    }

    public AnnouncementView create(AnnouncementModel announcementModel, Long userId) {
        UserView owner = userService.getById(userId);

        if (owner == null) {
            throw new NotFoundException(ErrorsEnum.USER_NOT_FOUND.getError());
        }

        GeolocationModel geolocationModel = getGeolocation(announcementModel.latitude, announcementModel.longitude);

        Announcement newAnnouncement = new Announcement();

        AnnouncementAdapter adapter = new AnnouncementAdapter(newAnnouncement, owner);

        adapter.mapModelToEntity(announcementModel, geolocationModel, userId);
        
        Announcement.persist(adapter.getAnnouncement());

        return adapter.mapEntityToView(null);
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

