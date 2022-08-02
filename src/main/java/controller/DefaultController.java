package controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.http.request.Request;
import webserver.http.response.Response;

import java.io.IOException;
import java.net.URISyntaxException;

public class DefaultController extends AbstractController {

    private static final Logger logger = LoggerFactory.getLogger(DefaultController.class);

    @Override
    public void doPost(Request request, Response response) {
    }

    @Override
    public void doGet(Request request, Response response) throws IOException, URISyntaxException {
        response.forward(request.getRequestPath());
    }
}
