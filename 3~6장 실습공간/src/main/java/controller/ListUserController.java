package controller;

import db.DataBase;
import request.HttpRequest;
import response.HttpResponseImpl;
import model.User;
import util.HttpRequestUtils;
import util.HttpSession;

import java.util.Collection;
import java.util.Map;


public class ListUserController extends AbstractController {

    @Override
    protected void doGet(HttpRequest request, HttpResponseImpl response) {
        if (!isLogin(request.getSession())) {
            response.sendRedirect("/user/login.html");
            return;
        }

        StringBuilder sb = new StringBuilder();
        Collection<User> userList = DataBase.findAll();
        sb.append("<table border='1'>");
        for(User user : userList) {
            sb.append("<tr>");
            sb.append("<td>" + user.getUserId() + "</td>");
            sb.append("<td>" + user.getName() + "</td>");
            sb.append("<td>" + user.getEmail() + "</td>");
            sb.append("</tr>");
        }
        response.forwardBody(sb.toString());
    }

    private boolean isLogin(HttpSession session) {
        Object user = session.getAttribute("user");
        if (user == null) {
            return false;
        }
        return true;
    }
}
