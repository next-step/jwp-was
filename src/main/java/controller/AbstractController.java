package controller;

import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;

import java.io.IOException;
import java.net.URISyntaxException;

public abstract class AbstractController implements Controller {

    public static final String ROOT_FILE = "/index.html";

    @Override
    public HttpResponse service(HttpRequest httpRequest) throws IOException, URISyntaxException {
        if (httpRequest.isPost()) {
            return doPost(httpRequest);
        }
        return doGet(httpRequest);
    }

    public HttpResponse doPost(HttpRequest httpRequest) {
        return HttpResponse.sendRedirect(ROOT_FILE);
    }

    public HttpResponse doGet(HttpRequest httpRequest) throws IOException, URISyntaxException {
        // TODO: forward로 수정해야됨.
        return HttpResponse.sendRedirect(httpRequest.getRequestPath());
    }
}
