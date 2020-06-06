package webserver;

import handler.StaticResourceHandler;
import handler.UserHandler;
import http.Method;
import http.request.HttpRequest;
import http.response.HttpResponse;

public class HttpRequestHandler {

    public HttpResponse handle(HttpRequest httpRequest) {
        if (httpRequest.getMethod() == Method.POST && httpRequest.getPath().startsWith("/user/create")) {
            UserHandler userHandler = new UserHandler();
            return userHandler.create(httpRequest);
        }

        return new StaticResourceHandler().get(httpRequest);
    }
}
