package next.controller;

import core.db.DataBase;
import core.mvc.Controller;
import next.model.User;
import next.utils.UserSessionUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class UpdateFormUserController implements Controller {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        User user = DataBase.findUserById(request.getParameter("userId"));
        request.setAttribute("user", user);
        String is = "true";

        HttpSession session = request.getSession();
        if (!UserSessionUtils.isSameUser(session, user)) {
            is = "false";
        }

        request.setAttribute("isSameUser", is);
        return "/user/form.jsp";
    }
}
