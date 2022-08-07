package webserver.controller;

import java.util.HashMap;
import java.util.Map;

public class RequestMapping {

    private static Map<String, Controller> map = new HashMap();

    static {
        map.put("/user/create", new UserCreateController());
        map.put("/user/login", new LoginController());
        map.put("/user/list", new UserListController());
    }

    public static Controller getController(String path) {
        if (map.containsKey(path)) {
            return map.get(path);
        }
        return null;
    }
}
