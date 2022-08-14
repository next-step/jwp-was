package webserver.controller;

import utils.FileIoUtils;
import webserver.http.header.Header;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;

import java.io.IOException;
import java.net.URISyntaxException;

public class TemplateController extends MethodController {
    private static final String TEMPLATE_PATH = "./templates";

    @Override
    public HttpResponse processGet(HttpRequest httpRequest) throws IOException, URISyntaxException {
        return HttpResponse.ok(
                Header.templateResponse(httpRequest.getSessionId()),
                FileIoUtils.loadFileFromClasspath(TEMPLATE_PATH + httpRequest.getPath())
        );
    }

    @Override
    HttpResponse processPost(HttpRequest httpRequest) {
        return HttpResponse.notFound();
    }

    @Override
    public boolean isMatchPath(HttpRequest httpRequest) {
        return httpRequest.isHtmlFilePath();
    }
}
