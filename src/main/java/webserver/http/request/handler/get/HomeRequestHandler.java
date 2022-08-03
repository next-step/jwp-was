package webserver.http.request.handler.get;

import java.io.IOException;
import java.net.URISyntaxException;

import utils.FileIoUtils;
import webserver.http.request.handler.RequestHandler;
import webserver.http.request.header.RequestHeader;
import webserver.http.response.header.ContentType;

public class HomeRequestHandler implements RequestHandler {
    private static final String REQUEST_URI = "/index.html";

    public static String requestUri() {
        return REQUEST_URI;
    }

    @Override
    public byte[] execute(RequestHeader requestHeader) throws IOException, URISyntaxException {
        return FileIoUtils.loadFileFromClasspath(ContentType.filePath(requestHeader.uri()));
    }
}
