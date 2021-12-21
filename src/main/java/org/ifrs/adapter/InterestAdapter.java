package org.ifrs.adapter;


import org.ifrs.entity.Interest;
import org.ifrs.model.InterestModel;
import org.ifrs.view.InterestView;
import org.ifrs.view.UserView;

public class InterestAdapter {
    private Interest interest;
    private UserView Interested;

    public InterestAdapter(Interest interest, UserView Interested) {
        this.interest = interest;
        this.Interested = Interested;
    }

    public InterestView mapEntityToView() {
        InterestView interestView = new InterestView();
        
        AnnouncementAdapter announcement = new AnnouncementAdapter(interest.getAnnouncement(), Interested);

        interestView.id = interest.getId();
        interestView.interested = Interested;
        interestView.announcement = announcement.mapEntityToView();
        interestView.status = interest.getStatus();

        return interestView;
    }

    public void mapModelToEntity(InterestModel interestModel) {
        interest.setInterestedId(interestModel.interestedId);
    }

    public Interest getInterest() {
        return this.interest;
    }
}
