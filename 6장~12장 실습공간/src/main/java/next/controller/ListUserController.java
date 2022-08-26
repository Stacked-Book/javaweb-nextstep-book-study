package next.controller;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import core.db.DataBase;
import core.mvc.Controller;
import next.utils.UserSessionUtils;

@WebServlet("/user/list")
public class ListUserController implements Controller {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession();
        if (!UserSessionUtils.isLogined(session)) {
            return "redirect:/user/loginForm";
        }

        request.setAttribute("users", DataBase.findAll());
        return "/user/list.jsp";
    }
}
