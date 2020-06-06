package handler;

import http.response.HttpResponse;
import http.Method;
import http.request.HttpRequest;
import http.response.EmptyHttpResponse;

public class HttpRequestHandler {

    public HttpResponse handle(HttpRequest httpRequest) {
        if (httpRequest.getMethod() == Method.POST && httpRequest.getPath().startsWith("/user/create")) {
            UserHandler userHandler = new UserHandler();
            userHandler.create(httpRequest);
            return new EmptyHttpResponse();
        }
        return new StaticResourceHandler().get(httpRequest);
    }
}
