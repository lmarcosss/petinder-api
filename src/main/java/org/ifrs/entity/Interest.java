package org.ifrs.entity;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import org.ifrs.enums.InterestStatusEnum;
import org.ifrs.model.InterestModel;

@Entity
public class Interest extends BaseEntity<InterestModel> {
    @Basic(optional = false)
    private String status;

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
        this.status = InterestStatusEnum.OPENNED.getStatus();
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public void mapFromEntity(InterestModel model) {}
}
