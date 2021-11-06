package org.ifrs.service;

import java.util.List;

import javax.ws.rs.NotFoundException;

import org.ifrs.entity.User;

public class UserService {
    public List<User> getAll() {
        return User.listAll();
    }

    public User getId(Integer id) {
        return User.findById(id);
    }

    public void update(User user) {
        User findedUser = User.findById(user.id);

        if (findedUser == null) {
            throw new NotFoundException("Usuário não encontrado");
        }

        findedUser.setName(user.getName());
    }

    public User create(User user) {
        user.persist();

        return user;
    }
}
