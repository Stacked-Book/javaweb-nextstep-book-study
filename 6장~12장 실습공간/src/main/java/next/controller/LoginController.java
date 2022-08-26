package next.controller;

import core.db.DataBase;
import core.mvc.Controller;
import next.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginController implements Controller {

    private static final Logger log = LoggerFactory.getLogger(LoginController.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        User user = DataBase.findUserById(request.getParameter("userId"));
        log.debug("user : {}", user);

        if (user == null && !(user.getPassword().equals(request.getParameter("password")))) {
            return "/user/login_failed.jsp";
        }

        HttpSession session = request.getSession();
        session.setAttribute("user", user);
        return "redirect:/user/list";
    }
}
