package next.web;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "dispatcher", urlPatterns = "/", loadOnStartup = 1)
public class DispatchServlet extends HttpServlet {
    private static Map<String, Controller> map = new HashMap<>();
    Controller controller;

    public DispatchServlet() {
        map.put("/user/login", new LoginServlet());
        map.put("/user/logout", new LogOutServlet());
        map.put("/user/updateForm", new UpdateUserFormServlet());
        map.put("/user/update", new UpdateUserServlet());
        map.put("/user/list", new ListUserServlet());
        map.put("/user/create", new CreateUserServlet());
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String requestURI = req.getRequestURI();
        if (!map.containsKey(requestURI)) {
            return;
        }
        controller = map.get(requestURI);
        controller.execute(req,resp);
    }
}
