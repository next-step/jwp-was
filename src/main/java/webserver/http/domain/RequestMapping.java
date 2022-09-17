package webserver.http.domain;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import static utils.FileIoUtils.loadFileFromClasspath;

public class RequestMapping {
    private final Map<String, Controller> controllerMap = new HashMap<>();
    private final RequestHeader requestHeader;
    private final RequestBody requestBody;

    public RequestMapping(RequestHeader requestHeader, RequestBody requestBody) {
        this.requestHeader = requestHeader;
        this.requestBody = requestBody;
        initMapping();
    }

    private void initMapping() {
        try {
            controllerMap.put("/index.html", new ForwardController(loadFileFromClasspath("./templates/index.html")));
            controllerMap.put("/user/form.html", new ForwardController(loadFileFromClasspath("./templates/user/form.html")));
            controllerMap.put("/user/login.html", new ForwardController(loadFileFromClasspath("./templates/user/login.html")));
            controllerMap.put("/user/login_failed.html", new ForwardController(loadFileFromClasspath("./templates/user/login_failed.html")));
            controllerMap.put("/user/create", new SignUpController());
            controllerMap.put("/user/login", new LoginController());
            controllerMap.put("/user/list", new UserListController());
        } catch (IOException | URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    public Controller controller(String url) {
        return controllerMap.get(url);
    }

    void put(String url, Controller controller) {
        controllerMap.put(url, controller);
    }

}
