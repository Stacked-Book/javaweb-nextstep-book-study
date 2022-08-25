package next.web;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import core.db.DataBase;
import next.model.User;

@WebServlet("/user/list")
public class ListUserServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if(!isLogined(req.getSession())) {
            req.setAttribute("loginFailed","not User");
            forward("/user/login.jsp", req, resp);
            return;
        }
        req.setAttribute("users", DataBase.findAll());
        RequestDispatcher rd = req.getRequestDispatcher("/user/list.jsp");
        rd.forward(req, resp);
    }

    private boolean isLogined(HttpSession session) {
        if (getUserFromSession(session) == null) {
            return false;
        }
        return true;
    }

    private User getUserFromSession(HttpSession session) {
        Object user = session.getAttribute("user");
        if (user == null) {
            return null;
        }
        return (User) user;
    }

    private void forward(String url, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher rd = req.getRequestDispatcher(url);
        rd.forward(req,resp);
    }

}
