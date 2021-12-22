package org.ifrs.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotFoundException;

import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.ifrs.adapter.AnnouncementAdapter;
import org.ifrs.adapter.InterestAdapter;
import org.ifrs.client.UserClient;
import org.ifrs.entity.Announcement;
import org.ifrs.entity.Interest;
import org.ifrs.enums.ErrorsEnum;
import org.ifrs.enums.InterestStatusEnum;
import org.ifrs.model.InterestModel;
import org.ifrs.view.AnnouncementView;
import org.ifrs.view.InterestView;
import org.ifrs.view.UserView;

@Singleton
public class InterestService {
    @Inject
    AnnouncementService announcementService;

    @Inject
    @RestClient
    UserClient userService;

    private InterestView formatAnnouncement(Interest interest) {
        UserView interested = userService.getById(interest.getInterestedId());

        UserView owner = userService.getById(interest.getAnnouncement().getOwnerId());

        AnnouncementView announcementView = new AnnouncementAdapter(interest.getAnnouncement(), owner).mapEntityToView();

        InterestAdapter adapter = new InterestAdapter(interest, interested);
        
        return adapter.mapEntityToView(announcementView);
    }

    private List<InterestView> formatInterests(List<Interest> interests) {
        return interests.stream().map(interest -> formatAnnouncement(interest)).collect(Collectors.toList());
    }

    public InterestView getById(Long id) {
        Interest findedInterest = Interest.findById(id);

        if (findedInterest == null) {
            throw new NotFoundException(ErrorsEnum.INTEREST_NOT_FOUND.getError());
        }

        InterestView interestFormatted = formatAnnouncement(findedInterest);

        return interestFormatted;
    }

    public InterestView create(InterestModel interest) {
        Announcement findedAnnouncement = Announcement.findById(interest.announcementId);

        if (findedAnnouncement == null) {
            throw new NotFoundException(ErrorsEnum.ANNOUNCEMENT_NOT_FOUND.getError());
        }

        UserView interested = userService.getById(interest.interestedId);

        if (interested == null) {
            throw new NotFoundException(ErrorsEnum.USER_NOT_FOUND.getError());
        }

        UserView owner = userService.getById(findedAnnouncement.getOwnerId());

        if (owner.id == interested.id) {
            throw new BadRequestException(ErrorsEnum.INTEREST_ANNOUNCEMENT_BAD_REQUEST.getError());
        }

        Interest findedInterest = Interest.find(
            "interestedId = ?1 and announcementId = ?2",
            interested.id,
            findedAnnouncement.getId()
        ).firstResult();

        if (findedInterest != null) {
            throw new BadRequestException(ErrorsEnum.INTEREST_DUPLICATION_BAD_REQUEST.getError());
        } 
        
        Interest newInterest = new Interest();

        AnnouncementView announcementView = new AnnouncementAdapter(findedAnnouncement, owner).mapEntityToView();

        InterestAdapter adapter = new InterestAdapter(newInterest, interested);

        adapter.mapModelToEntity(interest, findedAnnouncement);

        Interest.persist(adapter.getInterest());

        return adapter.mapEntityToView(announcementView);
    }

    public List<InterestView> getInterestsByAnnouncement(Long announcementId) {
        Announcement findedAnnouncement = Announcement.findById(announcementId);

        if (findedAnnouncement == null) {
            throw new NotFoundException(ErrorsEnum.ANNOUNCEMENT_NOT_FOUND.getError());
        }

        List<Interest> interests = Interest.find("announcement", findedAnnouncement).list();

        return formatInterests(interests);
    }

    public List<InterestView> getUserInterests(Long userId) {
        List<Interest> interests = Interest.find("interestedId", userId).list();

        return formatInterests(interests);
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
