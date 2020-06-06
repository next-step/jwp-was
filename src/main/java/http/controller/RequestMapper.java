package http.controller;

import http.requests.HttpRequest;
import http.types.HttpMethod;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class RequestMapper {

    private static final Map<RequestBranch, Controller> CONTROLLER_MAP = new HashMap<>();

    public static void addController(String path, HttpMethod method, Controller controller) {
        final RequestBranch branch = new RequestBranch(path, method);
        CONTROLLER_MAP.put(branch, controller);
    }

    public static Controller dispatch(HttpRequest request) {
        return CONTROLLER_MAP.get(new RequestBranch(request.getPath(), request.getMethod()));
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
