package webserver.controller;

import http.request.HttpRequest;
import http.response.HttpResponse;

public interface PostController extends Controller {

    @Override
    default HttpResponse service(HttpRequest request) {
        return doPost(request);
    }

    HttpResponse doPost(HttpRequest request);
}
