package webserver.http.request.handler;

import java.io.IOException;
import java.net.URISyntaxException;

import utils.FileIoUtils;
import webserver.http.request.header.RequestHeader;
import webserver.http.response.header.ContentType;

public class DefaultRequestHandler{

    public byte[] execute(RequestHeader requestHeader) throws IOException, URISyntaxException {
        return FileIoUtils.loadFileFromClasspath(ContentType.filePath(requestHeader.index()));
    }
}
