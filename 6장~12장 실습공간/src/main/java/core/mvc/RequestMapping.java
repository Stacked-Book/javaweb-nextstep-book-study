package core.mvc;

import core.mvc.Controller;
import core.mvc.DispatcherServlet;
import core.mvc.ForwardController;
import next.controller.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class RequestMapping {

    private static final Logger log =
            LoggerFactory.getLogger(DispatcherServlet.class);

    private Map<String, Controller> mappings = new HashMap<>();

    void initMapping() {
        mappings.put("/", new HomeController());
        mappings.put("/user/form", new ForwardController("/user/form.jsp"));
        mappings.put("/user/loginForm", new ForwardController("/user/login.jsp"));
        mappings.put("/user/list", new ForwardController("/user/list.jsp"));
        mappings.put("/user/create", new CreateUserController());
        mappings.put("/user/updateForm", new UpdateFormUserController());
        mappings.put("/user/update", new UpdateUserController());
        mappings.put("/user/login", new LoginController());
        mappings.put("/user/logout", new LogoutController());

        log.info("Initialized Request Mapping!");
    }

    public Controller findController(String url) {
        return mappings.get(url);
    }

    void put(String url, Controller controller) {
        mappings.put(url, controller);
    }
}
