package webserver.request;

import webserver.controller.RequestController;
import webserver.response.Response;

import java.util.Arrays;
import java.util.function.BiFunction;

public enum RequestApiPath {
    CREATE("/user/create", RequestController::create),
    LIST("/user/list", RequestController::list),
    LOGIN("/user/login", RequestController::login);

    String path;
    BiFunction<Request, Response, String> function;

    RequestApiPath(String path, BiFunction<Request, Response, String> function) {
        this.path = path;
        this.function = function;
    }

    public static String getViewName(Request request, Response response) {
        return Arrays.stream(RequestApiPath.values())
                .filter(value -> value.path.startsWith(request.getRequestPath()))
                .map(value -> value.function.apply(request, response))
                .findAny()
                .orElse("error/not_found");
    }
}
