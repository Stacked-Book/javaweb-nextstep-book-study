package util;

import db.DataBase;
import model.User;

import java.util.Collection;
import java.util.Map;

public class UserUtils {

    public static void addUser(Map<String, String> user) {
        User saveUser = new User(
                user.get("userId"),
                user.get("password"),
                user.get("name"),
                user.get("email")
        );
        System.out.println("add User : " + saveUser.toString());
        DataBase.addUser(saveUser);
    }

    public static boolean isUser(Map<String, String> user) {

        User findUser = DataBase.findUserById(user.get("userId"));
        if(findUser == null) { return false; }
        if(!findUser.getPassword().equals(user.get("password"))) { return false; }
        return true;
    }

    public static Collection<User> findAllUser() {
        return DataBase.findAll();
    }
}
