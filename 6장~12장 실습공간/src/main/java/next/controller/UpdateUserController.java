package next.controller;

import core.db.DataBase;
import core.mvc.Controller;
import next.model.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UpdateUserController implements Controller {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        User updateUser = new User(request.getParameter("userId"), request.getParameter("name"),
                request.getParameter("password"), request.getParameter("email"));

        DataBase.updateUser(updateUser);
        return "/user/list.jsp";
    }

}
