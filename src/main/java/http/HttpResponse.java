package http;

import utils.FileIoUtils;

import java.io.IOException;
import java.net.URISyntaxException;

public class HttpResponse {
    private static String PATH = "./templates";
    private static String CSS_PATH = "./static";

    public static byte[] getBody(String path) throws IOException {
        byte[] body = new byte[0];
        try {
            body = FileIoUtils.loadFileFromClasspath(PATH + path);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return body;
    }

    public static byte[] getCss(String path) throws IOException {
        byte[] body = new byte[0];
        try {
            body = FileIoUtils.loadFileFromClasspath(CSS_PATH + path);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return body;
    }
}
