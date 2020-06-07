package http;

import utils.FileIoUtils;

import java.io.IOException;
import java.net.URISyntaxException;

public class HttpResponse {
    private static String PATH = "./templates";

    public static byte[] getBody(String path) throws IOException, URISyntaxException {
        byte[] body = FileIoUtils.loadFileFromClasspath(PATH + path);
        return body;
    }
}
