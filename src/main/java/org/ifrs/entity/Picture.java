package org.ifrs.entity;

import javax.persistence.Basic;
import javax.persistence.Entity;

@Entity
public class Picture extends BaseEntity<Object> {
    @Basic(optional = false)
    private String url;

    public Picture() {}

    public Picture(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
