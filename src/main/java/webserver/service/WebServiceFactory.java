package webserver.service;

import java.util.HashMap;
import java.util.Map;

public class WebServiceFactory {
    private static final Map<String, WebService> webServices;
    static {
        webServices = new HashMap<>();
        webServices.put("/user/create",new UserService());
    }

    public static WebService create(String path) {
        if (path.lastIndexOf("/") == 0) {
            return null;
        }

        return webServices.get(path);
    }
}
