package webserver.controller;

import http.request.Protocol;
import utils.FileIoUtils;
import webserver.HttpHeader;
import webserver.request.HttpRequest;
import webserver.response.HttpResponse;
import webserver.response.HttpStatusCode;
import webserver.response.StatusLine;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Collections;

public class StaticController implements Controller {

    private final static String STATIC_PATH = "./static";

    @Override
    public HttpResponse service(HttpRequest request) throws IOException, URISyntaxException {
        return HttpResponse.of(
                StatusLine.of(Protocol.from("HTTP/1.1"), HttpStatusCode.OK),
                HttpHeader.from(Collections.singletonMap(
                        HttpHeader.CONTENT_TYPE, String.format("text/%s;charset=utf-8", fileExtension(request))
                )),
                FileIoUtils.loadFileFromClasspath(staticPath(request))
        );
    }

    private String fileExtension(HttpRequest request) {
        String path = request.getPath();
        return path.substring(request.getPath().lastIndexOf('.') + 1);
    }

    private String staticPath(HttpRequest request) {
        return STATIC_PATH + request.getPath();
    }
}
