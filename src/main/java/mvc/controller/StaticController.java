package mvc.controller;

import http.HttpHeader;
import http.request.HttpRequest;
import http.request.protocol.Protocol;
import http.response.HttpResponse;
import http.response.HttpStatusCode;
import http.response.StatusLine;
import utils.FileIoUtils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Collections;

public class StaticController extends AbstractController {

    private final static String STATIC_PATH = "./static";

    @Override
    public void doGet(HttpRequest request, HttpResponse response) throws IOException, URISyntaxException {
        response.buildResponse(StatusLine.of(Protocol.from("HTTP/1.1"), HttpStatusCode.OK),
                HttpHeader.from(Collections.singletonMap(HttpHeader.CONTENT_TYPE, String.format("text/%s;charset=utf-8",
                        fileExtension(request)))), FileIoUtils.loadFileFromClasspath(staticPath(request)));
    }

    private String fileExtension(HttpRequest request) {
        String path = request.getPath();
        return path.substring(request.getPath().lastIndexOf('.') + 1);
    }

    private String staticPath(HttpRequest request) {
        return STATIC_PATH + request.getPath();
    }
}
