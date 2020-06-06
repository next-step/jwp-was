package handler;

import http.request.HttpRequest;
import http.response.HttpResponse;
import http.view.StaticResourceView;

public class StaticResourceHandler {

    public HttpResponse get(HttpRequest httpRequest) {
        return new HttpResponse(new StaticResourceView(httpRequest.getPath()));
    }
}
