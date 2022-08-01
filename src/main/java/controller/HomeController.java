package controller;

import webserver.http.HttpMethod;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;

public class HomeController {

    public static final String path = "/index.html";
    private static final String viewPath = "/index.html";

    public HttpResponse run(HttpRequest httpRequest) {
        final HttpMethod httpMethod = httpRequest.getRequestLine().getMethod();

        if (httpMethod.equals(HttpMethod.GET)) {
            return doGet();
        } else {
            throw new IllegalArgumentException();
        }
    }

    private HttpResponse doGet() {
        return HttpResponse.getView(viewPath);
    }
}
