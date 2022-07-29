package webserver.handler;

import java.util.HashMap;
import java.util.Map;
import webserver.request.RequestLine;

public class RequestMapping {

    private RequestMapping() {
        throw new AssertionError();
    }

    private static final Map<RequestLine, Controller> CONTROLLERS = new HashMap<>();

    private static final String HTTP_1_1 = "HTTP/1.1";

    static {
        CONTROLLERS.put(RequestLine.of("POST", "/user/create", HTTP_1_1), new CreateUserController());
        CONTROLLERS.put(RequestLine.of("POST", "/user/login", HTTP_1_1), new LoginController());
        CONTROLLERS.put(RequestLine.of("GET", "/user/list.html", HTTP_1_1), new UserListController());
    }

    public static Controller map(final RequestLine requestLine) {
        if (CONTROLLERS.containsKey(requestLine)) {
            return CONTROLLERS.get(requestLine);
        }
        return new DefaultController();
    }
}
