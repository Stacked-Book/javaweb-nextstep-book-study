package core.db;

import java.util.Collection;
import java.util.Map;

import com.google.common.collect.Maps;

import next.model.User;

public class DataBase {
    private static Map<String, User> users = Maps.newHashMap();

    public static void addUser(User user) {
        users.put(user.getUserId(), user);
    }

    public static User updateUser(User updateUser) {
        User user = users.get(updateUser.getUserId());
        user.setName(updateUser.getName());
        user.setPassword(updateUser.getPassword());
        user.setEmail(updateUser.getEmail());
        return user;
    }

    public static User findUserById(String userId) {
        return users.get(userId);
    }

    public static Collection<User> findAll() {
        return users.values();
    }

}
