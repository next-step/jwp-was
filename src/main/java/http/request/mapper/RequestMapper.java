package http.request.mapper;

import http.controller.Controller;
import lombok.Getter;

import java.util.Arrays;
import java.util.Map;

import static http.controller.Controllers.*;
import static java.util.stream.Collectors.toMap;

public enum RequestMapper {
    CREATE_USER("/user/create", CREATE_USER_CONTROLLER),
    LIST_USER("/user/list", LIST_USER_CONTROLLER),
    LOGIN("/user/login", LOGIN_CONTROLLER),
    ;

    @Getter
    private String url;

    @Getter
    private Controller controller;

    private static final Map<String, Controller> requestMap = initRequestMap();

    private static Map<String, Controller> initRequestMap() {
        return Arrays.stream(values())
            .collect(toMap(RequestMapper::getUrl, RequestMapper::getController));
    }

    RequestMapper(String url, Controller controller) {
        this.url = url;
        this.controller = controller;
    }

    public static Controller mapRequest(String url) {
        return requestMap.getOrDefault(url, getStaticController(url));
    }

    private static Controller getStaticController(String url) {

    }
}
