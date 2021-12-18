package org.ifrs.service;

import java.util.List;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotFoundException;

import org.ifrs.adapter.InterestAdapter;
import org.ifrs.entity.Announcement;
import org.ifrs.entity.Interest;
import org.ifrs.enums.ErrorsEnum;
import org.ifrs.enums.InterestStatusEnum;
import org.ifrs.model.InterestModel;
import org.ifrs.view.InterestView;

public class InterestService {
    AnnouncementService announcementService = new AnnouncementService();

    private InterestView formatAnnouncement(Interest interest) {
        InterestAdapter adapter = new InterestAdapter(interest);
        
        return adapter.mapEntityToView();
    }

    private List<InterestView> formatInterests(List<Interest> interests) {
        return interests.stream().map(interest -> formatAnnouncement(interest)).toList();
    }

    public InterestView getById(Long id) {
        Interest findedInterest = Interest.findById(id);

        if (findedInterest == null) {
            throw new NotFoundException(ErrorsEnum.INTEREST_NOT_FOUND.getError());
        }

        InterestView interestFormatted = formatAnnouncement(findedInterest);

        return interestFormatted;
    }

    public Interest create(InterestModel interest) {
        Announcement findedAnnouncement = Announcement.findById(interest.announcementId);

        if (findedAnnouncement == null) {
            throw new NotFoundException(ErrorsEnum.ANNOUNCEMENT_NOT_FOUND.getError());
        }

        // if (findedAnnouncement.getOwner() == findedUser) {
        //     throw new BadRequestException(ErrorsEnum.INTEREST_ANNOUNCEMENT_BAD_REQUEST.getError());
        // }

        Interest findedInterest = Interest.find(
            "interestedId = ?1 and announcementId = ?2",
            interest.interestedId,
            findedAnnouncement.getId()
        ).firstResult();

        if (findedInterest != null) {
            throw new BadRequestException(ErrorsEnum.INTEREST_DUPLICATION_BAD_REQUEST.getError());
        } 
        
        Interest newInterest = new Interest();

        newInterest.setAnnouncement(findedAnnouncement);

        InterestAdapter adapter = new InterestAdapter(newInterest);

        Interest.persist(adapter.getInterest());

        return adapter.getInterest();
    }

    public List<InterestView> getInterestsByAnnouncement(Long announcementId) {
        Announcement findedAnnouncement = Announcement.findById(announcementId);

        if (findedAnnouncement == null) {
            throw new NotFoundException(ErrorsEnum.ANNOUNCEMENT_NOT_FOUND.getError());
        }

        List<Interest> interests = Interest.find("announcement", findedAnnouncement).list();

        return formatInterests(interests);
    }

    public List<Interest> getUserInterests(Long userId) {
        List<Interest> interests = Interest.find("interestedId", userId).list();

        return interests;
    }

    public void acceptInterestByAnnoucement(Long id) {
        Interest findedInterest = Interest.findById(id);

        if (findedInterest == null) {
            throw new NotFoundException(ErrorsEnum.INTEREST_NOT_FOUND.getError());
        }

        this.verifyStatusIfNotOpenned(findedInterest.getStatus());

        findedInterest.setStatus(InterestStatusEnum.ACCEPTED.getStatus());

        Interest.persist(findedInterest);
    }

    public void declineInterestByAnnoucement(Long id) {
        Interest findedInterest = Interest.findById(id);

        if (findedInterest == null) {
            throw new NotFoundException(ErrorsEnum.INTEREST_NOT_FOUND.getError());
        }

        this.verifyStatusIfNotOpenned(findedInterest.getStatus());

        findedInterest.setStatus(InterestStatusEnum.DECLINED.getStatus());

        Interest.persist(findedInterest);
    }

    private void verifyStatusIfNotOpenned(String status) {
        if (!status.equals(InterestStatusEnum.OPENNED.getStatus())) {
            throw new BadRequestException(ErrorsEnum.INTEREST_STATUS_BAD_REQUEST.getError());
        }
    }
}
