package requestcontroller;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class FrontController{
    private static Map<String, Controller> controllerMap = new ConcurrentHashMap<>();

    public FrontController() {
        initMapping();
    }

    private static void initMapping() {
        controllerMap.put("/user/create", new CreateController());
        controllerMap.put("/user/list", new ListController());
        controllerMap.put("/user/login", new LoginController());
    }

    public Controller getController(String url) {
        return controllerMap.get(url);
    }
}