package webserver.supporter;

import java.util.HashMap;
import java.util.Map;
import webserver.servlet.Servlet;
import webserver.servlet.UserCreateServlet;

public class SupportApis {
    private SupportApis() { }

    static Map<String, Servlet> apiMap;
    static {
        apiMap = new HashMap<>();
        apiMap.put("/user/create", new UserCreateServlet());
    }

    public static boolean isSupported(String path) {
        return apiMap.containsKey(path);
    }

    public static Servlet getServlet(String path) {
        return apiMap.get(path);
    }

}
