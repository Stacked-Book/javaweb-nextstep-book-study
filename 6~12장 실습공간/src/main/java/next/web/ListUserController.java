package next.web;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import core.db.DataBase;
import core.mvc.Controller;

//@WebServlet("/user/list")
public class ListUserController implements Controller {
    private static final long serialVersionUID = 1L;

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        if(!UserSessionUtils.isLogined(request.getSession())) {
/*
            req.setAttribute("loginFailed","not User");
            forward("/user/login.jsp", req, resp);
            return;
*/
            return "redirect:/user/loginForm";
        }
        request.setAttribute("users", DataBase.findAll());

/*
        RequestDispatcher rd = request.getRequestDispatcher("/user/list.jsp");
        rd.forward(req, resp);
*/
        return "/user/list.jsp";
    }

}
