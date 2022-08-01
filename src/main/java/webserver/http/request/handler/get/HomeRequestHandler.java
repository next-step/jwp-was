package webserver.http.request.handler.get;

import java.io.IOException;
import java.net.URISyntaxException;

import utils.FileIoUtils;
import webserver.http.request.handler.RequestHandler;
import webserver.http.response.header.ContentType;

public class HomeRequestHandler implements RequestHandler {
    private static final String REQUEST_INDEX = "/index.html";

    public static String requestIndex() {
        return REQUEST_INDEX;
    }

    @Override
    public byte[] execute() throws IOException, URISyntaxException {
        return FileIoUtils.loadFileFromClasspath(ContentType.filePath(REQUEST_INDEX));
    }
}
