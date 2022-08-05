package webserver.controller;

import utils.FileIoUtils;
import webserver.http.Header;
import webserver.http.request.HttpRequest;
import webserver.http.request.requestline.Method;
import webserver.http.response.HttpResponse;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Map;

public class StaticController implements Controller {
    private static final String STATIC_PATH = "./static";

    @Override
    public HttpResponse process(HttpRequest httpRequest) throws IOException, URISyntaxException {
        return HttpResponse.ok(
                new Header(Map.of("Content-Type", "text/css;charset=utf-8")),
                FileIoUtils.loadFileFromClasspath(STATIC_PATH + httpRequest.getPath())
        );
    }

    @Override
    public boolean isMatchRequest(HttpRequest httpRequest) {
        return httpRequest.isMethodEqual(Method.GET) && httpRequest.isStaticFilePath();
    }
}
