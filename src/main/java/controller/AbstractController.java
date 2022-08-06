package controller;

import webserver.http.HttpMethod;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;

public class AbstractController implements Controller {

    @Override
    public HttpResponse service(HttpRequest httpRequest) {
        HttpMethod httpMethod = httpRequest.getRequestLine().getMethod();
        if (httpMethod.equals(HttpMethod.GET)) {
            return doGet(httpRequest);
        }
        if (httpMethod.equals(HttpMethod.POST)) {
            return doPost(httpRequest);
        }
        return HttpResponse.notFound();
    }

    public HttpResponse doGet(HttpRequest httpRequest) {
        return HttpResponse.notFound();
    }

    public HttpResponse doPost(HttpRequest httpRequest) {
        return HttpResponse.notFound();
    }
}
