package org.ifrs.entity;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.ifrs.model.interestModel;

@Entity
public class Interest extends BaseEntity<interestModel> {
    @Basic(optional = false)
    private boolean isAproved;

    @Basic(optional = false)
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "interestedId")
    private User interested;

    @Basic(optional = false)
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "announcementId")
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
    public void mapFromEntity(interestModel model) {}
}
