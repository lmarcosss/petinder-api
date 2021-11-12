package org.ifrs.entity;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import org.ifrs.model.InterestModel;

@Entity
public class Interest extends BaseEntity<InterestModel> {
    @Basic(optional = false)
    private boolean isAproved;

    @Basic(optional = false)
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "interestedId")
    private User interested;

    @Basic(optional = false)
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "announcementId")
    @JsonManagedReference
    private Announcement announcement;
    
    public Interest() {
        this.isAproved = false;
    }

    public boolean isAproved() {
        return isAproved;
    }

    public void setAproved(boolean isAproved) {
        this.isAproved = isAproved;
    }

    public User getInterested() {
        return interested;
    }

    public void setInterested(User interested) {
        this.interested = interested;
    }

    public Announcement getAnnouncement() {
        return announcement;
    }

    public void setAnnouncement(Announcement announcement) {
        this.announcement = announcement;
    }

    @Override
    public void mapFromEntity(InterestModel model) {}
}
