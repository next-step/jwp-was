package webserver.http.domain;

import webserver.http.controller.*;

import java.util.HashMap;
import java.util.Map;

public class RequestMapping {
    private final Map<String, Controller> controllerMap = new HashMap<>();

    public RequestMapping() {
        initMapping();
    }

    private void initMapping() {
        controllerMap.put("/index.html", new ForwardController());
        controllerMap.put("/user/form.html", new ForwardController());
        controllerMap.put("/user/login.html", new ForwardController());
        controllerMap.put("/user/login_failed.html", new ForwardController());
        controllerMap.put("/user/create", new SignUpController());
        controllerMap.put("/user/login", new LoginController());
        controllerMap.put("/user/list", new UserListController());
        controllerMap.put("css", new StaticForwardController());
        controllerMap.put("js", new StaticForwardController());
        controllerMap.put("fonts", new StaticForwardController());
    }

    public Controller controller(String url) {
        if (staticUrl(url)) {
            url = url.substring(url.lastIndexOf(".") + 1);
        }

        return controllerMap.get(url);
    }

    private boolean staticUrl(String url) {
        return url.endsWith("css") || url.endsWith("js") || url.endsWith("ico") || url.endsWith("fonts");
    }
}
