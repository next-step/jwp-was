package webserver.http.mapping;

import utils.HttpStringType;
import webserver.controller.*;

import java.util.HashMap;
import java.util.Map;

public class RequestMapping {
    private static final Map<String, Controller> requestMapping;

    static {
        requestMapping = new HashMap<>();
        requestMapping.put("/", new HomeController());
        requestMapping.put("/index.html", new HomeController());
        requestMapping.put("/user/create", new UserCreateController());
        requestMapping.put("/user/login", new UserLoginController());
        requestMapping.put("/user/list.html", new UserListController());

        requestMapping.put("/user/form.html", new ForwardController());
        requestMapping.put("/user/login.html", new ForwardController());
        requestMapping.put("/user/login_failed.html", new ForwardController());
    }

    public static boolean support(String requestUri) {
        return requestMapping.containsKey(requestUri);
    }

    public static Controller getController(String requestUri) {
        return requestMapping.get(requestUri);
    }

    public static String getFilePath(String requestUri) {
        String filePath = HttpStringType.TEMPLATES_PREFIX.getType() + requestUri;

        if (filePath.endsWith(HttpStringType.FILE_PATH_EXT.getType())) {
            return filePath;
        }

        return filePath + HttpStringType.FILE_PATH_EXT.getType();
    }
}
