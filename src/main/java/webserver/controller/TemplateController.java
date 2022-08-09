package webserver.controller;

import utils.FileIoUtils;
import webserver.http.Header;
import webserver.http.request.HttpRequest;
import webserver.http.request.requestline.Method;
import webserver.http.response.HttpResponse;

import java.io.IOException;
import java.net.URISyntaxException;

public class TemplateController implements Controller {
    private static final String TEMPLATE_PATH = "./templates";

    @Override
    public HttpResponse process(HttpRequest httpRequest) throws IOException, URISyntaxException {
        return HttpResponse.ok(
                Header.templateResponse(),
                FileIoUtils.loadFileFromClasspath(TEMPLATE_PATH + httpRequest.getPath())
        );
    }

    @Override
    public boolean isMatchRequest(HttpRequest httpRequest) {
        return httpRequest.isMethodEqual(Method.GET) && httpRequest.isHtmlFilePath();
    }
}
