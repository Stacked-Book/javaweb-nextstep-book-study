package next.web;

import core.db.DataBase;
import next.dao.UserDao;
import next.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

public class UpdateUserServlet implements Controller{
    private static final Logger log = LoggerFactory.getLogger(CreateUserServlet.class);

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        User user = new User(req.getParameter("userId"), req.getParameter("password"), req.getParameter("name"),
                req.getParameter("email"));
        log.debug("user : {}", user);

        UserDao userDao = new UserDao();
        try {
            userDao.update(user);
        } catch (SQLException e) {
            log.error(e.getMessage());
        }

        return "/user/list";
    }
}
