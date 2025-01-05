package org.tuvarna.service;

import org.tuvarna.entity.Ticket;
import org.tuvarna.entity.User;
import org.tuvarna.repository.UserDAO;

import java.util.List;

public class UserService {
    private UserDAO userDAO;

    public UserService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public List<User> getAllUsers() {
        return userDAO.getUsers();
    }

    public User getUserByName(String name) {
        return userDAO.getUsers().stream()
                .filter(user -> user.getName()
                .equals(name)).findFirst().orElse(null);
    }

    public List<Ticket> getTicketsByUsername(String name) {
        return getUserByName(name).getTickets();
    }

}
