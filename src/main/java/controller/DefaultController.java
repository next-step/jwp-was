package controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;

import java.io.IOException;
import java.net.URISyntaxException;

public class DefaultController extends AbstractController {

    private static final Logger logger = LoggerFactory.getLogger(DefaultController.class);

    @Override
    public HttpResponse doGet(HttpRequest httpRequest) throws IOException, URISyntaxException {
        return HttpResponse.forward(httpRequest.getRequestPath());
    }
}
