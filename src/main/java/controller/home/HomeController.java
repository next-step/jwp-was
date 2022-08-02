package controller.home;

import webserver.http.HttpMethod;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;

public class HomeController {

    public static final String URL = "/index.html";
    public static final String VIEW_PATH = "/index.html";

    public HttpResponse run(HttpRequest httpRequest) {
        final HttpMethod httpMethod = httpRequest.getRequestLine().getMethod();

        if (httpMethod.equals(HttpMethod.GET)) {
            return doGet();
        } else {
            throw new IllegalArgumentException();
        }
    }

    private HttpResponse doGet() {
        return HttpResponse.getView(VIEW_PATH);
    }
}
