package webserver;

import http.httprequest.HttpRequest;
import http.httprequest.requestline.RequestLine;
import webserver.controller.Controller;
import webserver.controller.StaticController;
import webserver.controller.TemplateController;
import webserver.controller.UserCreateController;
import webserver.controller.UserListController;
import webserver.controller.UserLoginController;

import java.util.Set;

public class RequestMapper {

    private static final  Set<Controller> CONTROLLERS;

    static {
        CONTROLLERS = Set.of(new TemplateController(), new StaticController(), new UserCreateController(), new UserListController(), new UserLoginController());
    }

    private RequestMapper() {
        throw new AssertionError("'Assert' can not be instanced");
    }

    public static Controller getController(HttpRequest httpRequest) {
        RequestLine requestLine = httpRequest.getRequestLine();
        return CONTROLLERS.stream()
                .filter(it -> it.isMatch(requestLine.getHttpMethod(), requestLine.getPath()))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
