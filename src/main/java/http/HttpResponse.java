package http;

import utils.FileIoUtils;

import java.io.IOException;
import java.net.URISyntaxException;

public class HttpResponse {
    private static String PATH = "./templates";

    public static byte[] getBody(String path) throws IOException {
        byte[] body = new byte[0];
        try {
            body = FileIoUtils.loadFileFromClasspath(PATH + path);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return body;
    }
}
