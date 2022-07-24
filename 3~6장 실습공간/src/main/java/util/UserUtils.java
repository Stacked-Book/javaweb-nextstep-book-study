package util;

import model.User;

import java.util.Map;

public class UserUtils {

    public static User saveUser(Map<String, String> user) {
        user.get("userId");
        user.get("password");
        user.get("name");
        user.get("email");

        User saveUser = new User(
                user.get("userId"),
                user.get("password"),
                user.get("name"),
                user.get("email")
        );
        return saveUser;
    }
}
