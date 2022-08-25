package next.web;

import core.db.DataBase;
import next.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.text.html.Option;
import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Optional;

/**
 * 회원수정
 */
@WebServlet("/user/update/*")
public class UpdateUserFormServlet extends HttpServlet {
    private static final Logger log = LoggerFactory.getLogger(UpdateUserFormServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userId = req.getPathInfo().replace("/", "");
        log.debug("usedId: {}", userId);
        Optional<User> userById = Optional.ofNullable(DataBase.findUserById(userId));
        log.debug("userById: {}",userById);
        if (!userById.isPresent()) {
            resp.sendRedirect("/user/create");
            return;
        }
        req.setAttribute("user",DataBase.findUserById(userId));
        RequestDispatcher rd = req.getRequestDispatcher("/user/updateForm.jsp");
        rd.forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Optional<User> userById = Optional.ofNullable(DataBase.findUserById(req.getParameter("userId")));
        userById.orElseThrow(() -> new NoSuchElementException("찾을수없는 회원ID입니다"));
        DataBase.updateUser(req.getParameter("userId"), new User(req.getParameter("userId"), req.getParameter("password"),
                        req.getParameter("name"), req.getParameter("email")));
        resp.sendRedirect("/user/list");
    }
}
