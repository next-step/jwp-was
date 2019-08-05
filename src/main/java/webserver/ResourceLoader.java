package webserver;

import utils.FileIoUtils;

import java.io.IOException;
import java.net.URISyntaxException;

public class ResourceLoader {

    public static String getResourcePath(String path) {
        String prefix = "./templates";

        if (isStaticResource(path)) {
            prefix = "./static";
        }

        return prefix + path;
    }

    private static boolean isStaticResource(String path) {
        return path.contains("css") || path.contains("js") || path.contains("fonts");
    }

    public static byte[] loadResource(String path) throws IOException, URISyntaxException {
        return FileIoUtils.loadFileFromClasspath(path);
    }

    public static String resourceContentType(String path) {
        if(path.contains("js"))
            return "text/javascript;";

        if(path.contains("css"))
            return "text/css;";

        return "text/html;";
    }
}
