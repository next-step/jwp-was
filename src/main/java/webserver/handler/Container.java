package webserver.handler;

import com.google.common.collect.Maps;
import service.Controller;
import service.ResourceController;
import service.UserCreateController;
import service.UserListController;
import service.UserLoginController;
import webserver.request.HttpRequest;
import webserver.response.HttpResponse;
import webserver.response.HttpResponseFactory;

import java.util.Map;
import java.util.Objects;

public class Container {

    private static final Map<String, Controller> CONTAINER = Maps.newHashMap();

    private static final ResourceController RESOURCE_CONTROLLER = new ResourceController();

    static {
        CONTAINER.put("/user/list.html", new UserListController());
        CONTAINER.put("/user/create", new UserCreateController());
        CONTAINER.put("/user/login", new UserLoginController());
    }

    public static HttpResponse handle(HttpRequest request) {
        Controller controller = CONTAINER.get(request.getPathStr());
        if (Objects.nonNull(controller)) {
            return controller.doService(request);
        }

        if (RESOURCE_CONTROLLER.canServe(request)) {
            return RESOURCE_CONTROLLER.doService(request);
        }

        return HttpResponseFactory.response404();
    }
}
