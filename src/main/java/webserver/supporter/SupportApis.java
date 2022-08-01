package webserver.supporter;

import java.util.HashMap;
import java.util.Map;
import webserver.servlet.Controller;
import webserver.servlet.UserCreateController;
import webserver.servlet.UserListController;
import webserver.servlet.UserLoginController;

public class SupportApis {

    private SupportApis() {
    }

    static Map<String, Controller> apiMap;

    static {
        apiMap = new HashMap<>();
        apiMap.put("/user/create", new UserCreateController());
        apiMap.put("/user/login", new UserLoginController());
        apiMap.put("/user/list.html", new UserListController());
    }

    public static boolean isSupported(String path) {
        return apiMap.containsKey(path);
    }

    public static Controller getServlet(String path) {
        return apiMap.get(path);
    }

}
