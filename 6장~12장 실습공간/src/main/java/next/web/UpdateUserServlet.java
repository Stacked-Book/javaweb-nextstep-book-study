package next.web;

import core.db.DataBase;
import next.model.User;
import next.utils.UserSessionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/user/update")
public class UpdateUserServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private static final Logger log = LoggerFactory.getLogger(UpdateUserServlet.class);

    // update-form
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = DataBase.findUserById(request.getParameter("userId"));
        request.setAttribute("user", user);
        String is = "true";

        HttpSession session = request.getSession();
        if (!UserSessionUtils.isSameUser(session, user)) {
            is = "false";
        }

        request.setAttribute("isSameUser", is);

        RequestDispatcher rd = request.getRequestDispatcher("/user/form.jsp");
        rd.forward(request, response);
    }

    // update
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User updateUser = new User(req.getParameter("userId"), req.getParameter("name"),
                req.getParameter("password"), req.getParameter("email"));

        DataBase.updateUser(updateUser);
        resp.sendRedirect("/user/list");
    }
}
