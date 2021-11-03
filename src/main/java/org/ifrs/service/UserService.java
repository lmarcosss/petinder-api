package org.ifrs.service;

import java.util.List;
import org.ifrs.entity.User;
import org.ifrs.repository.UserRepository;

public class UserService {
    UserRepository userRepository = new UserRepository();

    public List<User> getAll() {
        return userRepository.getAll();
    }
}
