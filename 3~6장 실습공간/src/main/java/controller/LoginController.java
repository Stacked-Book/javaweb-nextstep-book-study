package controller;

import db.DataBase;
import request.HttpRequest;
import response.HttpResponseImpl;
import model.User;
import util.HttpSession;

public class LoginController implements Controller {

    @Override
    public void service(HttpRequest request, HttpResponseImpl response) {
        User user = DataBase.findUserById(request.getParameter("userId"));
        if (user != null) {
            if (user.login(request.getParameter("password"))) {
                HttpSession session = request.getSession();
                session.setAttribute("user", user);
                response.sendRedirect("/index.html");
            } else {
                response.sendRedirect("/user/login_failed.html");
            }
        } else {
            response.sendRedirect("/user/login_failed.html");
        }
    }
}
