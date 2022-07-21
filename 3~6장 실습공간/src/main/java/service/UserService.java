package service;

import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class UserService {
    private static final Logger log = LoggerFactory.getLogger(UserService.class);
    private final DataBase dataBase;

    public UserService(final DataBase dataBase) {
        this.dataBase = dataBase;
    }

    public void joinUser(final User user) {
        log.info("Join User and Save Database, User is {}", user.getName());
        dataBase.addUser(user);
    }

    public boolean login(String userId, String password) {
        try {
            User user = dataBase.findUserById(userId).orElseThrow(() -> new IllegalArgumentException("사용자가 준재하지 않습니다."));
            if (!user.getPassword().equals(password)) {
                throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
            }
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    public List<User> findAll() {
        return new ArrayList<>(dataBase.findAll());
    }
}
