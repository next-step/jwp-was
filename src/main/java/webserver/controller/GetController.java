package webserver.controller;

import http.request.HttpRequest;
import http.response.HttpResponse;

public interface GetController extends Controller {

    @Override
    default HttpResponse service(HttpRequest request) {
        return doGet(request);
    }

    HttpResponse doGet(HttpRequest request);
}
