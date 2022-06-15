package controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import service.GetResourceService;
import webserver.request.Request;
import webserver.response.Response;
import webserver.response.ResponseFactory;

import static webserver.request.RequestMethod.GET;

public class Container {
    private Container() {}

    private static final Map<String, Controller> container = new HashMap<>();

    static {
        container.put("GET /", new GetIndexController());
        container.put("GET /user/create", new GetUserCreateController());
        container.put("GET /user/login", new GetUserLoginController());
        container.put("GET /user/list.html", new GetUserListController());
        container.put("POST /user/create", new PostUserCreateController());
        container.put("POST /user/login", new PostUserLoginController());
    }

    public static Response handle(Request request) throws IOException {
        Controller controller = container.get(request.getRequestLineHash());
        if (controller != null) {
            return controller.service(request);
        }
        if (request.getMethod() == GET) {
            return GetResourceService.doGet(request);
        }
        return ResponseFactory.createNotImplemented();
    }
}
