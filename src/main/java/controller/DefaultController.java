package controller;

import java.io.IOException;
import service.ResourceService;
import webserver.request.Request;
import webserver.response.Response;
import webserver.response.ResponseFactory;

public class DefaultController extends AbstractController {
    @Override
    Response doGet(Request request) throws IOException {
        return ResourceService.getResource(request);
    }

    @Override
    Response doPost(Request Request) {
        return ResponseFactory.createNotImplemented();
    }
}
