package webserver.resource;

import utils.FileIoUtils;

import java.io.IOException;
import java.net.URISyntaxException;

public class ResourceResolver {
    private static final String PREFIX = "./static";

    public static byte[] getResource(String path) throws IOException, URISyntaxException {
        return FileIoUtils.loadFileFromClasspath(PREFIX + path);
    }
}
