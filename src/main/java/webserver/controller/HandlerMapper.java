package webserver.controller;

import enums.HttpStatusCode;
import java.util.List;
import webserver.request.HttpRequest;
import webserver.response.HttpResponse;

public class HandlerMapper {
    private static final String USER_CONTROLLER_PATH = "/user/create";

    public HttpResponse handle(HttpRequest httpRequest) throws Exception {
        List<Controller> controllerList = List.of(new ViewController(), new UserController());

        for (Controller controller : controllerList) {
            if (controller.canExecute(httpRequest)) {
                return controller.execute(httpRequest);
            }
        }

        return new HttpResponse(HttpStatusCode.NOT_FOUND);
    }
}
