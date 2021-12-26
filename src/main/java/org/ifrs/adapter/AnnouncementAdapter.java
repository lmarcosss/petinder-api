package org.ifrs.adapter;

import java.util.stream.Collectors;
import org.ifrs.entity.Announcement;
import org.ifrs.entity.Picture;
import org.ifrs.model.AnnouncementModel;
import org.ifrs.view.AnnouncementView;
import org.ifrs.view.UserView;

public class AnnouncementAdapter {
    private Announcement announcement;
    private UserView owner;
    
    public AnnouncementAdapter(Announcement announcement, UserView owner) {
        this.announcement = announcement;
        this.owner = owner;
    }

    public AnnouncementView mapEntityToView() {
        AnnouncementView announcementView = new AnnouncementView();

        announcementView.id = announcement.getId();
        announcementView.title = announcement.getTitle();
        announcementView.description = announcement.getDescription();
        announcementView.latitude = announcement.getLatitude();
        announcementView.longitude = announcement.getLongitude();
        announcementView.pictures = announcement.getPictures();
        announcementView.status = announcement.getStatus();
        announcementView.isClosed = announcement.isClosed();
        announcementView.owner = owner;

        return announcementView;
    }

    public void mapModelToEntity(AnnouncementModel announcementModel) {
        announcement.setDescription(announcementModel.description);
        announcement.setTitle(announcementModel.title);
        announcement.setLatitude(announcementModel.latitude);
        announcement.setLongitude(announcementModel.longitude);
        announcement.setOwnerId(announcementModel.userId);
        announcement.setPictures(announcementModel.pictures.stream().map(url -> new Picture(url)).collect(Collectors.toList()));
    }

    public Announcement getAnnouncement() {
        return this.announcement;
    }
}