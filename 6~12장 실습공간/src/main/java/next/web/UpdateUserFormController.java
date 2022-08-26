package next.web;

import core.db.DataBase;
import core.mvc.Controller;
import next.model.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UpdateUserFormController implements Controller {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String userId = request.getParameter("userId");
        User user = DataBase.findUserById(userId);
        User userFromSession = UserSessionUtils.getUserFromSession(request.getSession());

        if(!user.getUserId().equals(userFromSession.getUserId())) {
            throw new RuntimeException("사용자 정보가 일치하지 않습니다.");
        }
        request.setAttribute(UserSessionUtils.USER_SESSION_KEY, user);
        return "/user/update.jsp";
    }
}
