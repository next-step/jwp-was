package webserver.http.mapping;

import com.google.common.collect.Maps;
import utils.HttpStringType;
import utils.HttpStringUtils;
import webserver.controller.*;

import java.util.Map;

public class RequestMapping {
    private static final Map<String, Controller> controllers;
    private static final Map<String, Controller> requestMapping;

    static {
        controllers = Maps.newHashMap();
        controllers.put("home", new HomeController());
        controllers.put("userCreate", new UserCreateController());
        controllers.put("userLogin", new UserLoginController());
        controllers.put("userLogout", new UserLogoutController());
        controllers.put("userList", new UserListController());
        controllers.put("forward", new ForwardController());

        requestMapping = Maps.newHashMap();
        requestMapping.put("/", controllers.get("home"));
        requestMapping.put("/index.html", controllers.get("home"));
        requestMapping.put("/user/create", controllers.get("userCreate"));
        requestMapping.put("/user/login", controllers.get("userLogin"));
        requestMapping.put("/user/logout.html", controllers.get("userLogout"));
        requestMapping.put("/user/list.html", controllers.get("userList"));
        requestMapping.put("/user/form.html", controllers.get("forward"));
        requestMapping.put("/user/login.html", controllers.get("forward"));
        requestMapping.put("/user/login_failed.html", controllers.get("forward"));
    }

    public static boolean support(String requestUri) {
        return requestMapping.containsKey(requestUri);
    }

    public static Controller getController(String requestUri) {
        return requestMapping.get(requestUri);
    }

    public static String getFilePath(String requestUri) {
        String filePath = HttpStringUtils.concat(HttpStringType.PFX_TEMPLATES.getType(), requestUri);

        if (filePath.endsWith(HttpStringType.FILE_PATH_EXT.getType())) {
            return filePath;
        }

        return HttpStringUtils.concat(filePath, HttpStringType.FILE_PATH_EXT.getType());
    }
}
