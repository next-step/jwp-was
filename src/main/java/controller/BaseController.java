package controller;

import http.request.HttpRequest;
import http.response.HttpResponse;

public abstract class BaseController implements Controller {
    @Override
    public void service(final HttpRequest httpRequest, final HttpResponse httpResponse) {
        String method = httpRequest.getMethod();
        if (method.equals("GET")) {
            doGet(httpRequest, httpResponse);
        }
        doPost(httpRequest, httpResponse);
    }

    public void doPost(final HttpRequest httpRequest, final HttpResponse httpResponse) {
        httpResponse.sendRedirect("/index.html");
    }

    public void doGet(final HttpRequest httpRequest, final HttpResponse httpResponse) {
        httpResponse.forwardBody(httpRequest.getPath());
    }
}
