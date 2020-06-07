package webserver;

import handler.Handler;
import handler.HandlerMatcher;
import handler.HandlerRegister;
import handler.StaticResourceHandler;
import handler.UserCreateHandler;
import handler.UserListHandler;
import handler.UserLoginHandler;
import http.Method;
import http.request.HttpRequest;
import http.response.HttpResponse;

public class RequestHandlerMapping {
    private static final HandlerRegister handlerRegister = new HandlerRegister();
    static {
        handlerRegister.add(
            new HandlerMatcher(Method.POST, "\\/user\\/create"),
            new UserCreateHandler()
        );

        handlerRegister.add(
            new HandlerMatcher(Method.POST, "\\/user\\/login"),
            new UserLoginHandler()
        );

        handlerRegister.add(
            new HandlerMatcher(Method.GET, "\\/user\\/list\\.html"),
            new UserListHandler()
        );

        handlerRegister.add(
            new HandlerMatcher(Method.GET, ".*"),
            new StaticResourceHandler()
        );
    }

    public HttpResponse handle(HttpRequest httpRequest) {
        Handler handler = handlerRegister.find(httpRequest.getMethod(), httpRequest.getPath());
        return handler.handle(httpRequest);
    }

}
