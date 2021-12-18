package org.ifrs.view;

import javax.ws.rs.core.Response;

import org.ifrs.entity.Announcement;

public class InterestView {
    public Long id;
    public String status;
    public Response interested;
    public Announcement announcement;
}
