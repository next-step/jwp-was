package controller;

import http.HttpRequest;
import http.HttpResponse;
import http.HttpStatus;

public class IndexController extends AbstractController {
    public static final String TEMPLATES_INDEX_HTML = "./templates/index.html";

    @Override
    public HttpResponse doGet(HttpRequest httpRequest) {
        return HttpResponse.from(TEMPLATES_INDEX_HTML, HttpStatus.OK);
    }
}
