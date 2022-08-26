package next.web;

import core.db.DataBase;
import core.mvc.Controller;
import next.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UpdateUserController implements Controller {
    private static final Logger log = LoggerFactory.getLogger(UpdateUserController.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        User findUser = DataBase.findUserById(request.getParameter("userId"));
        User updateUser = findUser.updateUser(request.getParameter("password"),request.getParameter("name"),request.getParameter("email"));
        request.setAttribute(UserSessionUtils.USER_SESSION_KEY, updateUser);
        return "/user/list.jsp";
    }
}
