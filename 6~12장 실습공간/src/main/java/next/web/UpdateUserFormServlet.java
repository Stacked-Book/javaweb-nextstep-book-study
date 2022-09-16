package next.web;

import core.db.DataBase;
import next.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

/**
 * 회원수정
 */
public class UpdateUserFormServlet implements Controller {
    private static final Logger log = LoggerFactory.getLogger(UpdateUserFormServlet.class);

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String userId = req.getPathInfo().replace("/", "");
        log.debug("usedId: {}", userId);
        Optional<User> userById = Optional.ofNullable(DataBase.findUserById(userId));
        log.debug("userById: {}",userById);
        if (!userById.isPresent()) {
            return "redirect:/user/create";
        }
        req.setAttribute("user",DataBase.findUserById(userId));
        return "/user/updateForm.jsp";
    }
}
