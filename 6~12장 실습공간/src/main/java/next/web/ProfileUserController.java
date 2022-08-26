package next.web;

import core.db.DataBase;
import core.mvc.Controller;
import next.model.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ProfileUserController implements Controller {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String userId = request.getParameter("userId");
        System.out.println("userId  : " + userId);
        User user = DataBase.findUserById(userId);
        if (user == null) throw new RuntimeException("사용자를 찾을 수 없습니다.");
        request.setAttribute(UserSessionUtils.USER_SESSION_KEY, user);
        return "/user/profile.jsp";
    }
}
