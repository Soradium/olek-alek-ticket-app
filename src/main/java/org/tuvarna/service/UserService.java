package org.tuvarna.service;

import org.tuvarna.entity.Ticket;
import org.tuvarna.entity.User;
import org.tuvarna.factories.FactoryDAO;
import org.tuvarna.repository.TableDAO;

import java.util.List;

public class UserService {

    private final TableDAO<User> userDAO;

    public UserService() {
        this.userDAO = FactoryDAO.getInstance().getDao(User.class);
    }

    public List<User> getAllUsers() {
        return userDAO.findAll();
    }

    public User getUserByName(String name) {
        return userDAO.findAll().stream()
                .filter(user -> user.getName()
                .equals(name)).findFirst().orElse(null);
    }

    public void addUser(User user) {
        userDAO.save(user);
    }
}
