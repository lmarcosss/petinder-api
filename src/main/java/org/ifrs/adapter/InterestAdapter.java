package org.ifrs.adapter;

import javax.ws.rs.core.Response;

import org.ifrs.entity.Announcement;
import org.ifrs.entity.Interest;
import org.ifrs.model.InterestModel;
import org.ifrs.view.InterestView;

public class InterestAdapter {
    private Interest interest;
    private Response Interested;

    // public InterestAdapter(Interest interest, Announcement announcement, Response Interested) {
    //     this.interest = interest;
    //     this.announcement = announcement;
    //     this.Interested = Interested;
    // }

    public InterestAdapter(Interest interest) {
        this.interest = interest;
    }

    public InterestView mapEntityToView() {
        InterestView interestView = new InterestView();

        interestView.id = interest.getId();
        interestView.interested = Interested;
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
