package org.ifrs.view;

import java.util.List;

import org.ifrs.entity.Picture;

public class AnnouncementView {
    public Long id;
    public String title;
    public String description;
    public UserView owner;
    public double latitude;
    public double longitude;
    public List<Picture> pictures;
    public String status;
    public boolean isClosed;
}
