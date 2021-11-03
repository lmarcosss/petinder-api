package org.ifrs.repository;

import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import org.ifrs.entity.User;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

@ApplicationScoped
public class UserRepository implements PanacheRepository<User> {
    public List<User> getAll() {
        return listAll();
    }
}
