package webserver;

import handler.StaticResourceHandler;
import handler.UserCreateHandler;
import handler.UserListHandler;
import handler.UserLoginHandler;
import http.Method;
import http.request.HttpRequest;
import http.response.HttpResponse;

public class RequestHandlerMapping {
    private final UserCreateHandler userCreateHandler = new UserCreateHandler();
    private final UserLoginHandler userLoginHandler = new UserLoginHandler();
    private final UserListHandler userListHandler = new UserListHandler();
    private final StaticResourceHandler staticResourceHandler = new StaticResourceHandler();

    public HttpResponse handle(HttpRequest httpRequest) {
        if (httpRequest.getMethod() == Method.POST && httpRequest.getPath().startsWith("/user/create")) {
            return userCreateHandler.handle(httpRequest);
        }

        if(httpRequest.getMethod() == Method.POST && httpRequest.getPath().startsWith("/user/login")){
            return userLoginHandler.handle(httpRequest);
        }

        if(httpRequest.getMethod() == Method.GET && httpRequest.getPath().startsWith("/user/list.html")){
            return userListHandler.handle(httpRequest);
        }

        return staticResourceHandler.handle(httpRequest);
    }
}
