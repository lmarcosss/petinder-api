package org.ifrs.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonBackReference;

import org.ifrs.enums.AnnouncementStatusEnum;
import org.ifrs.model.AnnouncementModel;

@Entity
public class Announcement extends BaseEntity<AnnouncementModel> {
    @Basic(optional = false)
    private double longitude;
    
    @Basic(optional = false)
    private double latitude;

    @Basic(optional = false)
    private String title;
    
    @Basic(optional = false)
    private String description;
    
    @Basic(optional = false)
    private String status;
    
    @Basic(optional = false)
    private boolean isClosed;
    
    @Basic(optional = false)
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "announcementId")
    private List<Picture> pictures;

    @Basic(optional = false)
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "announcementId")
    @JsonBackReference
    private List<Interest> interests;

    @Basic(optional = false)
    private Long ownerId;

    public Announcement() {
        this.pictures = new ArrayList<>();
        this.status = AnnouncementStatusEnum.OPENNED.getStatus();
        this.isClosed = false;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public List<Interest> getInterests() {
        return interests;
    }

    public void setInterests(List<Interest> interests) {
        this.interests = interests;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    public boolean isClosed() {
        return isClosed;
    }

    public void setClosed(boolean isClosed) {
        this.isClosed = isClosed;
    }
}
