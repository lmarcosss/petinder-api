package org.ifrs.service;

import javax.ws.rs.NotFoundException;

import org.ifrs.entity.Announcement;
import org.ifrs.entity.Interest;
import org.ifrs.entity.User;
import org.ifrs.enums.ErrorsEnum;
import org.ifrs.model.interestModel;

public class InterestService {
    UserService userService = new UserService();
    AnnouncementService announcementService = new AnnouncementService();

    public Interest getById(Long userId) {
        return Interest.findById(userId);
    }

    public Interest create(interestModel interest) {
        User findedUser = User.findById(interest.interestedId);

        if (findedUser == null) {
            throw new NotFoundException(ErrorsEnum.USER_NOT_FOUND.getError());
        }

        Announcement findedAnnouncement = Announcement.findById(interest.announcementId);

        if (findedAnnouncement == null) {
            throw new NotFoundException(ErrorsEnum.ANNOUNCEMENT_NOT_FOUND.getError());
        }

        Interest newInterest = new Interest();

        newInterest.setInterested(findedUser);

        newInterest.setAnnouncement(findedAnnouncement);

        Interest.persist(newInterest);

        return newInterest;
    }
}
