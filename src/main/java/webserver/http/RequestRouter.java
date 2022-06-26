package webserver.http;

import webserver.http.controller.Controller;
import webserver.http.controller.NotFoundController;
import webserver.http.controller.ResourceController;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class RequestRouter {
    private static final ResourceController resourceController = new ResourceController();
    private static final NotFoundController notFoundController = new NotFoundController();

    private final Map<String, Controller> controllers = new HashMap<>();

    public RequestRouter add(final String path, final Controller controller) {
        this.controllers.put(path, controller);
        return this;
    }

    public void route(final HttpRequest request, final HttpResponse response) throws IOException {
        final Controller routedController = this.getRoutedController(request);
        routedController.service(request, response);
    }

    public Controller getRoutedController(final HttpRequest request) {
        final Controller routedController = this.controllers.getOrDefault(request.getPath(), null);

        if (routedController != null) {
            return routedController;
        }

        if (this.isResourceRequest(request)) {
            return resourceController;
        }

        return notFoundController;
    }

    private boolean isResourceRequest(final HttpRequest request) {
        final String path = request.getPath();

        return path.endsWith(".html") ||
                path.endsWith(".css") ||
                path.endsWith(".js") ||
                path.endsWith(".ico") ||
                path.contains("/fonts");
    }
}
