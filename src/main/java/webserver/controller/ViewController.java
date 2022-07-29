package webserver.controller;

import enums.HttpStatusCode;
import java.io.IOException;
import java.net.URISyntaxException;
import utils.FileIoUtils;
import webserver.request.HttpRequest;
import webserver.response.HttpResponse;

public class ViewController implements Controller {
    private static final String VIEW_PATH = "./templates";

    @Override
    public HttpResponse execute(HttpRequest httpRequest) throws Exception {
        return new HttpResponse(
                HttpStatusCode.OK,
                FileIoUtils.loadFileFromClasspath(VIEW_PATH + httpRequest.getPath())
        );
    }
}
