package webserver;

import handler.StaticResourceHandler;
import handler.UserCreateHandler;
import handler.UserLoginHandler;
import http.Method;
import http.request.HttpRequest;
import http.response.HttpResponse;

public class HttpRequestHandler {
    private final UserCreateHandler userCreateHandler = new UserCreateHandler();
    private final UserLoginHandler userLoginHandler = new UserLoginHandler();
    private final StaticResourceHandler staticResourceHandler = new StaticResourceHandler();

    public HttpResponse handle(HttpRequest httpRequest) {
        if (httpRequest.getMethod() == Method.POST && httpRequest.getPath().startsWith("/user/create")) {
            return userCreateHandler.handle(httpRequest);
        }

        if(httpRequest.getMethod() == Method.POST && httpRequest.getPath().startsWith("/user/login")){
            return userLoginHandler.handle(httpRequest);
        }

        return staticResourceHandler.handle(httpRequest);
    }
}
