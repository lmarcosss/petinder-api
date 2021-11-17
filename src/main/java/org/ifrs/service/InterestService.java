package org.ifrs.service;

import java.util.List;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.NotFoundException;

import org.ifrs.entity.Announcement;
import org.ifrs.entity.Interest;
import org.ifrs.entity.User;
import org.ifrs.enums.ErrorsEnum;
import org.ifrs.model.InterestModel;

public class InterestService {
    UserService userService = new UserService();
    AnnouncementService announcementService = new AnnouncementService();

    public Interest getById(Long userId) {
        return Interest.findById(userId);
    }

    public Interest create(InterestModel interest) throws ClientErrorException {
        User findedUser = User.findById(interest.interestedId);

        if (findedUser == null) {
            throw new NotFoundException(ErrorsEnum.USER_NOT_FOUND.getError());
        }

        Announcement findedAnnouncement = Announcement.findById(interest.announcementId);

        if (findedAnnouncement == null) {
            throw new NotFoundException(ErrorsEnum.ANNOUNCEMENT_NOT_FOUND.getError());
        }

        if (findedAnnouncement.getOwner() == findedUser) {
            throw new BadRequestException(ErrorsEnum.INTEREST_BAD_REQUEST.getError());
        }

        Interest newInterest = new Interest();

        newInterest.setInterested(findedUser);

        newInterest.setAnnouncement(findedAnnouncement);

        Interest.persist(newInterest);

        return newInterest;
    }

    public List<Interest> getInterestsByAnnouncement(Long id) {
        List<Interest> interests = Interest.find("announcementId", id).list();

        return interests;
    }
}
