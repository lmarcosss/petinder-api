package org.ifrs.service;

import java.util.List;
import javax.ws.rs.NotFoundException;

import org.ifrs.entity.User;
import org.ifrs.model.UserModel;
import org.ifrs.enums.ErrorsEnum;

public class UserService {
    public List<User> listAll() {
        return User.listAll();
    }

    public User getById(Long userId) {
        return User.findById(userId);
    }

    public void update(UserModel user, Long id) {
        User findedUser = this.getById(id);

        findedUser.mapFromEntity(user);

        User.persist(findedUser);
    }

    public User create(UserModel user) {
        User newUser = new User();

        newUser.mapFromEntity(user);
        User.persist(newUser);

        return newUser;
    }
}
