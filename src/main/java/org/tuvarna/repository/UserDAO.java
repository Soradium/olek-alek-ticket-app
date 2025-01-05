package org.tuvarna.repository;

import org.tuvarna.entity.User;

import java.util.List;

public interface UserDAO {
    User getUserById(int id);
    List<User> getUsers();
    User addUser(User User);
}
