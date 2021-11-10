package org.ifrs.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import org.ifrs.enums.AnnouncementStatusEnum;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

@Entity
public class Announcement extends PanacheEntity {
    private double longitude;
    private double latitude;
    private String description;
    private String status;
    private boolean isClosed;
    
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "announcementId")
    private List<Picture> pictures;
    
    public Announcement() {
        this.pictures = new ArrayList<>();
        this.status = AnnouncementStatusEnum.OPENNED.getStatus();
    }

    public List<Picture> getPictures() {
        return pictures;
    }

    public void setPictures(List<Picture> pictures) {
        this.pictures = pictures;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean getIsClosed() {
        return isClosed;
    }

    public void setIsClosed(boolean isClosed) {
        this.isClosed = isClosed;
    }
}
