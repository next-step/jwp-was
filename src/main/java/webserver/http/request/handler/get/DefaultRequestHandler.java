package webserver.http.request.handler.get;

import java.io.IOException;
import java.net.URISyntaxException;

import utils.FileIoUtils;
import webserver.http.request.handler.RequestHandler;
import webserver.http.request.header.RequestHeader;
import webserver.http.response.header.ContentType;

public class DefaultRequestHandler implements RequestHandler {
    private final RequestHeader requestHeader;

    public DefaultRequestHandler(RequestHeader requestHeader) {
        this.requestHeader = requestHeader;
    }

    @Override
    public byte[] execute() throws IOException, URISyntaxException {
        return FileIoUtils.loadFileFromClasspath(ContentType.filePath(requestHeader.uri()));
    }
}
