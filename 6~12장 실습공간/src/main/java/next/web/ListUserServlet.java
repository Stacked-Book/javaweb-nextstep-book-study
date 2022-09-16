package next.web;

import next.dao.UserDao;
import next.model.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class ListUserServlet implements Controller {

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        Optional<Object> session = Optional.ofNullable(req.getSession().getAttribute("use"));
        if (!session.isPresent()) {
            return "redirect:/user/login";
        }

        UserDao userDao = new UserDao();
        List<User> users;
        try {
            users = userDao.findAll();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        req.setAttribute("users", users);

        return "/user/list.jsp";
    }
}
