package webserver.controller;

import http.HttpMethod;
import http.request.HttpRequest;
import http.response.HttpResponse;

public abstract class AbstractController implements Controller {
    @Override
    public void service(HttpRequest httpRequest, HttpResponse httpResponse) {
        HttpMethod httpMethod = httpRequest.getMethod();
        if (isGet(httpMethod)) {
            doGet(httpRequest, httpResponse);
        } else if (isPost(httpMethod)) {
            doPost(httpRequest, httpResponse);
        }
    }

    protected boolean isGet(HttpMethod httpMethod) {
        return httpMethod == HttpMethod.GET;
    }

    protected boolean isPost(HttpMethod httpMethod) {
        return httpMethod == HttpMethod.POST;
    }

    protected abstract void doGet(HttpRequest httpRequest, HttpResponse httpResponse);
    protected abstract void doPost(HttpRequest httpRequest, HttpResponse httpResponse);
}
