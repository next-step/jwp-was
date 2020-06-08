package webserver.dispatcher;

import http.controller.*;
import http.requests.HttpRequest;
import http.types.HttpMethod;

import java.util.HashMap;
import java.util.Map;

public class RequestDispatcher {

    private static final ControllerMap CONTROLLER_MAP;

    static {
        final Map<RequestBranch, Controller> map = new HashMap<>();
        map.put(new RequestBranch("/user/create", HttpMethod.POST), new SignUpController());
        map.put(new RequestBranch("/user/login", HttpMethod.POST), new SignInController());
        map.put(new RequestBranch("/user/list", HttpMethod.GET), new UserListController());
        CONTROLLER_MAP = new ControllerMap(map);
    }

    public static Controller dispatch(HttpRequest request) {
        final String path = request.getPath();
        final Controller controller = CONTROLLER_MAP.getController(request);
        if (controller != null) {
            return controller;
        }
        return DefaultController.getControllerWithPath(path).controller;
    }

    private enum DefaultController {
        TEMPLATE_CONTROLLER(new TemplateController()),
        STATIC_CONTROLLER(new StaticController()),
        NOT_FOUND_CONTROLLER(new NotFoundController());

        private final Controller controller;

        DefaultController(Controller controller) {
            this.controller = controller;
        }

        private static DefaultController getControllerWithPath(String path) {
            if (path.endsWith(".html")) {
                return TEMPLATE_CONTROLLER;
            }

            if (isStaticPath(path)) {
                return STATIC_CONTROLLER;
            }

            return NOT_FOUND_CONTROLLER;
        }

        private static boolean isStaticPath(String path) {
            return path.endsWith(".css") ||
                    path.startsWith("/fonts") ||
                    path.startsWith(".png") ||
                    path.endsWith(".js");
        }
    }
}
