package org.ifrs.adapter;

import org.ifrs.entity.User;
import org.ifrs.view.UserView;

public class UserAdapter {
    private User user;
    
    public UserAdapter(User user) {
        this.user = user;
    }

    public UserView mapEntityToView() {
        UserView userView = new UserView();

        userView.id = user.getId();
        userView.name = user.getName();
        userView.birthDay = user.getBirthDay();
        userView.phone = user.getPhone();
        userView.description = user.getDescription();

        return userView;
    }
}