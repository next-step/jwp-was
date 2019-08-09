package request;

import controller.UserController;
import webserver.http.HttpMethod;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;

import java.util.*;
import java.util.function.BiFunction;

/**
 * 유저가 작성
 */
public class RequestMap {
    private static final Map<String, BiFunction<HttpRequest, HttpResponse, Optional<String>>> routerMap = new HashMap<>();
    static {
        routerMap.put(HttpMethod.POST + "/user/create", UserController::createUser);
        routerMap.put(HttpMethod.POST + "/user/login", UserController::login);
        routerMap.put(HttpMethod.GET + "/user/list", UserController::list);
    }

    public static Set<String> keySet() {
        return routerMap.keySet();
    }

    public static BiFunction<HttpRequest, HttpResponse, Optional<String>> get(String key) {
        return routerMap.get(key);
    }
}
