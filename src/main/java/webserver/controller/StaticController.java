package webserver.controller;

import utils.FileIoUtils;
import webserver.http.HttpHeaders;
import webserver.http.request.HttpMethod;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;
import webserver.http.response.HttpStatusCode;
import webserver.http.response.ResponseHeader;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Collections;

public class StaticController implements Controller {

    private static final String STATIC_PATH = "./static";

    @Override
    public boolean isMatch(HttpRequest request) {
        return request.matchMethod(HttpMethod.GET)
                && StaticController.class.getClassLoader().getResource(staticPath(request)) != null;
    }

    @Override
    public HttpResponse execute(HttpRequest request) throws IOException, URISyntaxException {
        return HttpResponse.of(
                HttpStatusCode.OK,
                ResponseHeader.from(Collections.singletonMap(HttpHeaders.CONTENT_TYPE, String.format("text/%s;charset=utf-8", fileExtension(request)))),
                FileIoUtils.loadFileFromClasspath(staticPath(request))
        );
    }

    private String fileExtension(HttpRequest request) {
        String path = request.path().toString();
        return path.substring(request.path().toString().lastIndexOf('.') + 1);
    }

    private String staticPath(HttpRequest request) {
        return STATIC_PATH + request.path().toString();
    }
}
