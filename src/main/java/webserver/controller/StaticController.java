package webserver.controller;

import utils.FileIoUtils;
import webserver.http.Header;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;

import java.io.IOException;
import java.net.URISyntaxException;

public class StaticController extends MethodController {
    private static final String STATIC_PATH = "./static";

    @Override
    public HttpResponse processGet(HttpRequest httpRequest) throws URISyntaxException, IOException {
        return HttpResponse.ok(
                Header.staticResponse(),
                FileIoUtils.loadFileFromClasspath(STATIC_PATH + httpRequest.getPath())
        );
    }

    @Override
    HttpResponse processPost(HttpRequest httpRequest) {
        return HttpResponse.notFound();
    }

    @Override
    public boolean isMatchPath(HttpRequest httpRequest) {
        return httpRequest.isStaticFilePath();
    }
}
