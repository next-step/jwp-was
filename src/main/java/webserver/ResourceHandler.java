package webserver;

import utils.FileIoUtils;
import webserver.http.request.RequestUri;

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
        return path.contains("css") || path.contains("js") || path.contains("fonts");
    }

    public static byte[] loadResource(String path) throws IOException, URISyntaxException {
        return FileIoUtils.loadFileFromClasspath(path);
    }

    public static String resourceContentType(RequestUri uri) {
        if(uri.getPath().contains("js"))
            return "text/javascript;";
        if(uri.getPath().contains("css"))
            return "text/css;";

        return "text/html;";
    }
}
