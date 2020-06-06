package handler;

import http.response.HttpResponse;
import http.Method;
import http.request.HttpRequest;

public class HttpRequestHandler {

    public HttpResponse handle(HttpRequest httpRequest) {
        if (httpRequest.getMethod() == Method.POST && httpRequest.getPath().startsWith("/user/create")) {
            UserHandler userHandler = new UserHandler();
            return userHandler.create(httpRequest);
        }
        return new StaticResourceHandler().get(httpRequest);
    }
}
