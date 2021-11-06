package org.ifrs.entity;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

@Entity
public class Interest extends PanacheEntity {
    @Basic(optional = false)
    private boolean isAproved;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "interestedId")
    private User interested;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "announcementId")
    private Announcement announcement;

    public User getInterested() {
        return interested;
    }

    public void setInterested(User interested) {
        this.interested = interested;
    }

    public boolean isAproved() {
        return isAproved;
    }

    public void setAproved(boolean isAproved) {
        this.isAproved = isAproved;
    }
}
