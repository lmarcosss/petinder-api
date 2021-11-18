package org.ifrs.service;

import java.util.List;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotFoundException;

import org.ifrs.entity.Announcement;
import org.ifrs.entity.Interest;
import org.ifrs.entity.User;
import org.ifrs.enums.ErrorsEnum;
import org.ifrs.enums.InterestStatusEnum;
import org.ifrs.model.InterestModel;

public class InterestService {
    UserService userService = new UserService();
    AnnouncementService announcementService = new AnnouncementService();

    public Interest getById(Long userId) {
        Interest findedInterest = Interest.findById(userId);

        if (findedInterest == null) {
            throw new NotFoundException(ErrorsEnum.INTEREST_NOT_FOUND.getError());
        }

        return findedInterest;
    }

    public Interest create(InterestModel interest) {
        User findedUser = userService.getById(interest.interestedId);
        Announcement findedAnnouncement = announcementService.getById(interest.announcementId);

        if (findedAnnouncement.getOwner() == findedUser) {
            throw new BadRequestException(ErrorsEnum.INTEREST_ANNOUNCEMENT_BAD_REQUEST.getError());
        }

        Interest findedInterest = Interest.find(
            "interestedId = ?1 and announcementId = ?2",
            findedUser.getId(),
            findedAnnouncement.getId()
        ).firstResult();

        if (findedInterest != null) {
            throw new BadRequestException(ErrorsEnum.INTEREST_DUPLICATION_BAD_REQUEST.getError());
        } 
        
        Interest newInterest = new Interest();

        newInterest.setInterested(findedUser);

        newInterest.setAnnouncement(findedAnnouncement);

        Interest.persist(newInterest);

        return newInterest;
    }

    public List<Interest> getInterestsByAnnouncement(Long announcementId) {
        Announcement findedAnnouncement = announcementService.getById(announcementId);

        List<Interest> interests = Interest.find("announcement", findedAnnouncement).list();

        return interests;
    }

    public List<Interest> getUserInterests(Long userId) {
        User findedUser = userService.getById(userId);

        List<Interest> interests = Interest.find("interested", findedUser).list();

        return interests;
    }

    public void acceptInterestByAnnoucement(Long id) {
        Interest findedInterest = this.getById(id);

        this.verifyStatusIsNotOpenned(findedInterest.getStatus());

        findedInterest.setStatus(InterestStatusEnum.ACCEPTED.getStatus());

        Interest.persist(findedInterest);
    }

    public void declineInterestByAnnoucement(Long id) {
        Interest findedInterest = this.getById(id);

        this.verifyStatusIsNotOpenned(findedInterest.getStatus());

        findedInterest.setStatus(InterestStatusEnum.DECLINED.getStatus());

        Interest.persist(findedInterest);
    }

    private void verifyStatusIsNotOpenned(String status) {
        if (!status.equals(InterestStatusEnum.OPENNED.getStatus())) {
            throw new BadRequestException(ErrorsEnum.INTEREST_STATUS_BAD_REQUEST.getError());
        }
    }
}
