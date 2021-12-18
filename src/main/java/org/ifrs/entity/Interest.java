package org.ifrs.entity;

import javax.persistence.Basic;
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
    private Long interestedId;

    @Basic(optional = false)
    @ManyToOne
    @JoinColumn(name = "announcementId")
    @JsonManagedReference
    private Announcement announcement;

    public Interest() {
        this.status = InterestStatusEnum.OPENNED.getStatus();
    }
    
    public Long getInterestedId() {
        return interestedId;
    }

    public void setInterestedId(Long interestedId) {
        this.interestedId = interestedId;
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
}
