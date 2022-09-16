package next.web;

import core.db.DataBase;
import next.model.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Optional;

/**
 * 회원 로그인
 */

public class LoginServlet implements Controller {

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String requestUserId = req.getParameter("userId");
        Optional<User> userById = Optional.ofNullable(DataBase.findUserById(requestUserId));
        userById.orElseThrow(() -> new NoSuchElementException("존재하지않는 회원입니다 "));

        // 로그인 성공시
        if (DataBase.findUserById(requestUserId).getUserId().equals(requestUserId) &&
                DataBase.findUserById(requestUserId).getPassword().equals(req.getParameter("password"))) {
            HttpSession session = req.getSession();
            session.setAttribute("user",DataBase.findUserById(requestUserId));
        }
        return "redirect:/";
    }
}
