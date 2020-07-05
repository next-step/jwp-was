package http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedHashMap;
import java.util.Map;

public class RequestHandlerMapping {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandlerMapping.class);

    private static Map<String, Controller> handlers = new LinkedHashMap<>();

    public static void init() {
        handlers.put("GET /user/create", new UserCreateController());
        handlers.put("POST /user/create", new UserCreateController());
        handlers.put("POST /user/login", new UserLoginController());
        handlers.put("GET /user/list", new UserListController());
    }

    public static Controller getController(final HttpRequest request) {
        final String handler = request.getHandler();
        return handlers.getOrDefault(handler, new ResourceController());
    }
}
