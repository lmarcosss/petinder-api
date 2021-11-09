package org.ifrs.entity;

import javax.persistence.Basic;
import javax.persistence.Entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

@Entity
public class Interest extends PanacheEntity {
    @Basic(optional = false)
    private boolean isAproved;

    public boolean isAproved() {
        return isAproved;
    }

    public void setAproved(boolean isAproved) {
        this.isAproved = isAproved;
    }
}
