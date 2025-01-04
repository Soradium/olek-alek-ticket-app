package org.tuvarna.repository;

import org.tuvarna.entity.User;

import java.util.List;

public interface UserDAO {
    User getUserByName(String name);
    List<User> getUsers();
    void addUser(User User);
    void updateUser(User User);
    void deleteUser(User User);
}
