package webserver;

import exception.WebServerException;
import http.controller.CSSController;
import http.controller.Controller;
import http.controller.CreateUserController;
import http.controller.FontController;
import http.controller.HTMLController;
import http.controller.ImageController;
import http.controller.JSController;
import http.controller.ListUserController;
import http.controller.LoginUserController;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Created by iltaek on 2020/06/11 Blog : http://blog.iltaek.me Github : http://github.com/iltaek
 */
public class RequestMapping {

    private static final String CREATE_CONTROLLER_PATH = "/user/create";
    private static final String LOGIN_CONTROLLER_PATH = "/user/login";
    private static final String LIST_CONTROLLER_PATH = "/user/list";
    private static final String HTML_CONTROLLER_PATH = "^[\\S]+(\\.html|\\.ico)";
    private static final String CSS_CONTROLLER_PATH = "^[\\S]+\\.css";
    private static final String FONT_CONTROLLER_PATH = "^[\\S]+(\\.eot|\\.woff|\\.woff2|\\.svg|\\.ttf)";
    private static final String IMG_CONTROLLER_PATH = "^[\\S]+(\\.png|\\.jpeg|\\.gif)";
    private static final String JS_CONTROLLER_PATH = "^[\\S]+\\.js";
    private static final String NO_CONTROLLER_MATCH = "해당 요청을 처리할 수 없습니다.";

    private static Map<String, Controller> controllers = new HashMap<>();

    static {
        controllers.put(CREATE_CONTROLLER_PATH, new CreateUserController());
        controllers.put(LOGIN_CONTROLLER_PATH, new LoginUserController());
        controllers.put(LIST_CONTROLLER_PATH, new ListUserController());
        controllers.put(HTML_CONTROLLER_PATH, new HTMLController());
        controllers.put(CSS_CONTROLLER_PATH, new CSSController());
        controllers.put(FONT_CONTROLLER_PATH, new FontController());
        controllers.put(IMG_CONTROLLER_PATH, new ImageController());
        controllers.put(JS_CONTROLLER_PATH, new JSController());
    }

    public static Controller getController(String path) {
        for (String controllerKey : controllers.keySet()) {
            if (Pattern.matches(controllerKey, path)) {
                return controllers.get(controllerKey);
            }
        }
        throw new WebServerException(NO_CONTROLLER_MATCH);
    }
}
