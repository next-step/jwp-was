package http;

import java.util.LinkedHashMap;
import java.util.Map;

public class RequestHandlerMapping {

    private static Map<RequestLine, Controller> handlers = new LinkedHashMap<>();

    public static void init() {
        handlers.put(new RequestLine(Method.GET, "/user/create", "http/1.1"), new UserCreateController());
        handlers.put(new RequestLine(Method.POST, "/user/create", "http/1.1"), new UserCreateController());
        handlers.put(new RequestLine(Method.POST, "/user/login", "http/1.1"), new UserLoginController());
        handlers.put(new RequestLine(Method.POST, "/user/login", "http/1.1"), new UserLoginController());
        handlers.put(new RequestLine(Method.GET, "/user/list", "http/1.1"), new UserListController());
    }

    public static Controller getController(final HttpRequest request) {
        return handlers.getOrDefault(request.getRequestLine(), new ResourceController());
    }
}
