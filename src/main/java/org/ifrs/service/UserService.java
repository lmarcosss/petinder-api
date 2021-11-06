package org.ifrs.service;

import javax.ws.rs.NotFoundException;

import org.ifrs.entity.User;

public class UserService {
    public User getById(Integer userId) {
        return User.findById(userId);
    }

    public void update(User user) {
        User findedUser = User.findById(user.id);

        if (findedUser == null) {
            throw new NotFoundException("Usuário não encontrado");
        }

        findedUser.setName(user.getName());
        findedUser.setBirthDay(user.getBirthDay());
        findedUser.setCpf(user.getCpf());
        findedUser.setPhone(user.getPhone());
        findedUser.setDescription(user.getDescription());
        findedUser.setEmail(user.getEmail());
        findedUser.setPassword(user.getPassword());

        findedUser.persist();
    }

    public User create(User user) {
        user.persist();

        return user;
    }
}
