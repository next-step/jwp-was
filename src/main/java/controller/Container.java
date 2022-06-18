package controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import webserver.request.Request;
import webserver.response.Response;

public class Container {
    private Container() {}

    private static final Map<String, Controller> container = new HashMap<>();

    static {
        container.put("/", new RedirectIndexController());
        container.put("/user/create", new CreateUserController());
        container.put("/user/login", new LoginController());
        container.put("/user/list.html", new ListUserController());
    }

    public static Response handle(Request request) throws IOException {
        return container.getOrDefault(
                request.getPath(),
                new DefaultController()
        ).service(request);
    }
}
