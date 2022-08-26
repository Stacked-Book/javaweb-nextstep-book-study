package next.utils;

import next.model.User;
import org.apache.commons.lang3.ObjectUtils;

import javax.servlet.http.HttpSession;
import java.util.Objects;

public class UserSessionUtils {

    private static final String USER_SESSION_KEY = "user";

    public static User getUserFromSession(HttpSession httpSession) {
        Object user = httpSession.getAttribute(USER_SESSION_KEY);
        if (user == null) {
            return null;
        }
        return (User) user;
    }

    public static boolean isLogined(HttpSession httpSession) {
        if (getUserFromSession(httpSession) == null) {
            return false;
        }
        return true;
    }

    public static boolean isSameUser(HttpSession httpSession, User user) {
        if (!isLogined(httpSession)) {
            return false;
        }

        User sessionUser = getUserFromSession(httpSession);

        if (user == null && sessionUser == null) {
            return false;
        }

        if (!(Objects.equals(sessionUser.getUserId(), user.getUserId()))) {
            return false;
        }

        return true;
    }
}
