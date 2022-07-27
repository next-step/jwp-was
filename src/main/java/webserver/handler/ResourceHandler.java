package webserver.handler;

import utils.FileIoUtils;
import webserver.Handler;
import webserver.http.*;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Map;

public class ResourceHandler implements Handler {

    private final static Map<String, String> CONTENT_TYPE_BY_FILE = Map.of(
            "html", "text/html",
            "css", "text/css",
            "js", "application/javascript"
    );

    @Override
    public boolean isSupport(Request request) {
        String path = request.getPath();

        String extension = getExtension(path);

        return !"".equals(extension);
    }

    @Override
    public Response handle(Request request) {
        String path = request.getPath();

        byte[] bytes = loadFile(path);

        Response response = new Response(bytes);
        response.setContentType(CONTENT_TYPE_BY_FILE.getOrDefault(getExtension(path), "text/html"));
        return response;
    }

    private byte[] loadFile(String path) {
        try {
            return FileIoUtils.loadFileFromClasspath("./templates" + path);
        } catch (IOException | URISyntaxException e) {
            throw new RuntimeException();
        }
    }

    private String getExtension(String path) {
        String[] split = path.split("/");

        String resourceName = split[split.length - 1];

        String[] split1 = resourceName.split("\\.");

        if (split1.length < 2) {
            return "";
        }

        return split1[1];
    }

}
