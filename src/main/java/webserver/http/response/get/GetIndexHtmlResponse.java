package webserver.http.response.get;

import java.io.IOException;
import java.net.URISyntaxException;

import utils.FileIoUtils;

public class GetIndexHtmlResponse{
    public byte[] response(String filePath, String index) {
        try {
            return FileIoUtils.loadFileFromClasspath(filePath + index);
        } catch (IOException | URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }
}
