package controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;

public class DefaultController extends AbstractController {

    private static final Logger logger = LoggerFactory.getLogger(DefaultController.class);

    @Override
    public HttpResponse doGet(HttpRequest httpRequest) {
        // TODO: forward로 수정해야됨.
        return HttpResponse.sendRedirect(httpRequest.getRequestPath());
    }
}
