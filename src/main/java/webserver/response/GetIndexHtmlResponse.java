package webserver.response;

import java.io.IOException;
import java.net.URISyntaxException;

import utils.FileIoUtils;

public class GetIndexHtmlResponse{
    public byte[] response() {
        try {
            return FileIoUtils.loadFileFromClasspath("./templates/index.html");
        } catch (IOException | URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }
}
