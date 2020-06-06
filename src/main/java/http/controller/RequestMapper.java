package http.controller;

import http.requests.HttpRequest;
import http.types.HttpMethod;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class RequestMapper {

    private static final Map<RequestBranch, Controller> CONTROLLER_MAP = new HashMap<>();
    private static final Controller STATIC_CONTROLLER = new StaticController();
    private static final Controller TEMPLATE_CONTROLLER = new TemplateController();
    private static final Controller NOT_FOUND_CONTROLLER = new NotFoundController();

    public static void addController(String path, HttpMethod method, Controller controller) {
        final RequestBranch branch = new RequestBranch(path, method);
        CONTROLLER_MAP.put(branch, controller);
    }

    public static Controller dispatch(HttpRequest request) {
        final String path = request.getPath();
        Controller controller = CONTROLLER_MAP.get(new RequestBranch(path, request.getMethod()));
        if (controller == null && path.endsWith(".html")) {
            controller = TEMPLATE_CONTROLLER;
        }

        final String accept = request.getHeader("Accept");
        if (controller == null && accept != null && accept.contains("text/css")) {
            controller = STATIC_CONTROLLER;
        }

        if (controller == null) {
            controller = NOT_FOUND_CONTROLLER;
        }
        return controller;
    }

    private static class RequestBranch {

        private final String path;
        private final HttpMethod method;

        public RequestBranch(String path, HttpMethod method) {
            this.path = path;
            this.method = method;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            RequestBranch that = (RequestBranch) o;
            return Objects.equals(path, that.path) &&
                    method == that.method;
        }

        @Override
        public int hashCode() {
            return Objects.hash(path, method);
        }
    }
}
