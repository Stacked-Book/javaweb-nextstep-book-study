package next.web;

import core.db.DataBase;
import core.mvc.Controller;
import next.model.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LoginUserController implements Controller {
    private static final long serialVersionUID = 1L;

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String userId = request.getParameter("userId");
        String password = request.getParameter("password");
        User user = DataBase.findUserById(userId);
        if (user == null) {
            System.out.println("### LoginUserController user null");
            request.setAttribute("loginFailed","not User");
            return "/user/login.jsp";
        }
        if (!user.getPassword().equals(password)) {
            System.out.println("### LoginUserController password not same");
            request.setAttribute("loginFailed","Password Not Match");
            return "/user/login.jsp";
        } else {
            System.out.println("### LoginUserController user login");
            HttpSession session = request.getSession();
            session.setAttribute(UserSessionUtils.USER_SESSION_KEY, user);
            return "redirect:/";
        }
    }
}
