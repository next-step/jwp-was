package webserver;

import http.Response.CSSResponse;
import http.Response.FontResponse;
import http.Response.HTMLResponse;
import http.Response.HandlebarsResponse;
import http.Response.HttpResponse;
import http.Response.ImageResponse;
import http.Response.JSResponse;
import http.controller.Controller;
import http.controller.CreateUserController;
import http.controller.ListUserController;
import http.controller.LoginUserController;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by iltaek on 2020/06/09 Blog : http://blog.iltaek.me Github : http://github.com/iltaek
 */
public class RequestMapping {

    private static final String CREATE_CONTROLLER_PATH = "/user/create";
    private static final String LOGIN_CONTROLLER_PATH = "/user/login";
    private static final String LIST_CONTROLLER_PATH = "/user/list";

    private static Map<String, Controller> controllers = new HashMap<>();
    private static List<HttpResponse> responses = new ArrayList<>();
    private static HttpResponse htmlResponse = new HTMLResponse();

    static {
        controllers.put(CREATE_CONTROLLER_PATH, new CreateUserController());
        controllers.put(LOGIN_CONTROLLER_PATH, new LoginUserController());
        controllers.put(LIST_CONTROLLER_PATH, new ListUserController());

        responses.add(new CSSResponse());
        responses.add(new FontResponse());
        responses.add(new ImageResponse());
        responses.add(new JSResponse());
        responses.add(new HandlebarsResponse());
    }

    public static Controller getController(String path) {
        return controllers.get(path);
    }

    public static HttpResponse getResponse(String path) {
        return responses.stream()
            .filter(response -> response.isMyResponse(path))
            .findFirst()
            .orElse(htmlResponse);
    }
}
