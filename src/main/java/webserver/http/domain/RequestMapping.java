package webserver.http.domain;

import java.util.HashMap;
import java.util.Map;

public class RequestMapping {
    private final Map<String, Controller> controllerMap = new HashMap<>();

    public RequestMapping() {
        initMapping();
    }

    private void initMapping() {
        controllerMap.put("/index.html", new ForwardController("./templates/index.html"));
        controllerMap.put("/user/form.html", new ForwardController("./templates/user/form.html"));
        controllerMap.put("/user/login.html", new ForwardController("./templates/user/login.html"));
        controllerMap.put("/user/login_failed.html", new ForwardController("./templates/user/login_failed.html"));
        controllerMap.put("/user/create", new SignUpController());
        controllerMap.put("/user/login", new LoginController());
        controllerMap.put("/user/list", new UserListController());
    }

    public Controller controller(String url) {
        return controllerMap.get(url);
    }
}
