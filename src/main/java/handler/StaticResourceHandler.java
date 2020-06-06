package handler;

import http.request.HttpRequest;
import http.response.HttpResponse;
import http.view.StaticResourceView;

public class StaticResourceHandler implements Handler{

    @Override
    public HttpResponse handle(HttpRequest httpRequest) {
        return new HttpResponse(new StaticResourceView(httpRequest.getPath()));
    }
}
