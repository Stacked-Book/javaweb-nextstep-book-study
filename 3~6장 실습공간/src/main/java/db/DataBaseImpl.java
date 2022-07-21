package db;

import com.google.common.collect.Maps;
import model.User;
import service.DataBase;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;

public class DataBaseImpl implements DataBase {
    private static final Map<String, User> users = Maps.newHashMap();

    public void addUser(final User user) {
        users.put(user.getUserId(), user);
    }

    public Optional<User> findUserById(final String userId) {
        return Optional.ofNullable(users.get(userId));
    }

    public Collection<User> findAll() {
        return users.values();
    }
}
