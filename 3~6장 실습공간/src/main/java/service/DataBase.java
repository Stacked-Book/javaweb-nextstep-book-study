package service;

import model.User;

import java.util.Collection;
import java.util.Optional;

public interface DataBase {

    void addUser(final User user);

    Optional<User> findUserById(final String userId);

    Collection<User> findAll();
}
