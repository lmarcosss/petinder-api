package org.ifrs.adapter;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.ifrs.entity.Announcement;
import org.ifrs.entity.Picture;
import org.ifrs.model.AnnouncementModel;
import org.ifrs.model.GeolocationModel;
import org.ifrs.view.AnnouncementView;
import org.ifrs.view.UserView;

public class AnnouncementAdapter {
    private Announcement announcement;
    private UserView owner;
    
    public AnnouncementAdapter(Announcement announcement, UserView owner) {
        this.announcement = announcement;
        this.owner = owner;
    }

    public AnnouncementView mapEntityToView(String interestStatus, Long loggedUserId) {
        AnnouncementView announcementView = new AnnouncementView();

        announcementView.id = announcement.getId();
        announcementView.title = announcement.getTitle();
        announcementView.description = announcement.getDescription();
        announcementView.city = announcement.getCity();
        announcementView.state = announcement.getState();
        announcementView.pictures = announcement.getPictures();
        announcementView.status = announcement.getStatus();
        announcementView.isClosed = announcement.isClosed();
        announcementView.owner = owner;
        announcementView.interestStatus = interestStatus;
        announcementView.isMyAnnouncement = owner.id == loggedUserId;

        return announcementView;
    }

    public void mapModelToEntity(AnnouncementModel announcementModel, GeolocationModel geolocationModel, Long ownerId) {
        String city = Stream.of(geolocationModel.getCity(), geolocationModel.getTown(), geolocationModel.getVillage())
            .filter(location -> location != null)
            .findFirst()
            .orElse("");

        announcement.setDescription(announcementModel.description);
        announcement.setTitle(announcementModel.title);
        announcement.setCity(city);
        announcement.setState(geolocationModel.address.state);
        announcement.setOwnerId(ownerId);
        announcement.setPictures(announcementModel.pictures.stream().map(url -> new Picture(url)).collect(Collectors.toList()));
    }

    public Announcement getAnnouncement() {
        return this.announcement;
    }
}