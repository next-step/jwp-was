package http;

import java.util.LinkedHashMap;
import java.util.Map;

public class RequestHandlerMapping {

    private static Map<String, Controller> handlers = new LinkedHashMap<>();

    public static void init() {
        handlers.put("POST /user/create", new UserController());
        handlers.put("POST /user/login", new UserController());
        handlers.put("GET /user/list", new UserController());
    }

    public static Controller getController(final HttpRequest request) {
        return handlers.getOrDefault(request.getHandler(), new ResourceController());
    }
}
