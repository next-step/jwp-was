package controller;

import webserver.http.HttpMethod;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;

public class LogInController {

    public static final String path = "/user/login.html";
    public static final String viewPath = "/user/login.html";
    public static final String successRedirectPath = "/index.html";
    public static final String failRedirectPath = "/user/login_failed.html";

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
