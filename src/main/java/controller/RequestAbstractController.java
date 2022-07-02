package controller;

import http.*;

public abstract class RequestAbstractController implements RequestController {
    @Override
    public HttpResponse service(HttpRequest request) throws Exception {
        RequestMethod method = request.getRequestLine().getMethod();

        if (method == RequestMethod.POST) {
            return doPost(request);
        }

        return doGet(request);
    }

    @Override
    public HttpResponse doGet(HttpRequest request) throws Exception {
        return new HttpResponse(HttpStatus. NOT_FOUND, MediaType.TEXT_HTML_UTF8, "", null);
    }

    @Override
    public HttpResponse doPost(HttpRequest request) throws Exception {
        return new HttpResponse(HttpStatus. NOT_FOUND, MediaType.TEXT_HTML_UTF8, "", null);
    }
}
