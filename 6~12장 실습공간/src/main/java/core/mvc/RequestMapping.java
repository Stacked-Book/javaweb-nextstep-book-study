package core.mvc;

import next.web.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class RequestMapping {
    private static final Logger logger = LoggerFactory.getLogger(DispatcherServlet.class);
    private Map<String, Controller> mappings = new HashMap<>();

    void initMapping() {
        mappings.put("/", new HomeController());
        mappings.put("/user/form", new ForwardController("/user/form.jsp"));
        mappings.put("/user/loginForm", new ForwardController("/user/login.jsp"));
        mappings.put("/user/create", new CreateUserController());
        mappings.put("/user/login", new LoginUserController());
        mappings.put("/user/list", new ListUserController());
        mappings.put("/user/logout", new LogoutUserController());
        mappings.put("/user/profile", new ProfileUserController());
        mappings.put("/user/update", new UpdateUserController());
        mappings.put("/user/updateForm", new UpdateUserFormController());
    }

    public Controller findController(String requestUri) {
        return mappings.get(requestUri);
    }
}
