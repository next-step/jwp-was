package webserver;

import utils.FileIoUtils;
import webserver.http.RequestUri;

import java.io.IOException;
import java.net.URISyntaxException;

public class ResourceHandler {

    public static String getResourcePath(RequestUri uri) {
        String prefix = "./templates";

        if (isStaticResource(uri.getPath())) {
            prefix = "./static";
        }

        return prefix + uri.getPath();
    }

    private static boolean isStaticResource(String path) {
        return path.contains("css") || path.contains("js");
    }

    public static byte[] loadResource(String path) throws IOException, URISyntaxException {

        return FileIoUtils.loadFileFromClasspath(path);
    }
}
