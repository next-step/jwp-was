package webserver.http.request.handler.get;

import java.io.IOException;
import java.net.URISyntaxException;

import utils.FileIoUtils;
import webserver.http.request.handler.RequestHandler;
import webserver.http.response.header.ContentType;

public class UserFormRequestHandler implements RequestHandler {
    private static final String REQUEST_URI = "/user/form.html";

    public static String requestUri() {
        return REQUEST_URI;
    }

    @Override
    public byte[] execute() throws IOException, URISyntaxException {
        return FileIoUtils.loadFileFromClasspath(ContentType.filePath(REQUEST_URI));
    }
}