package webserver.handler;

import utils.FileIoUtils;
import webserver.LoadFileException;
import webserver.Handler;
import webserver.http.*;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Map;

public class StaticFileHandler implements Handler {

    private final static Map<String, String> CONTENT_TYPE_BY_FILE = Map.of(
            "html", "text/html",
            "css", "text/css",
            "js", "application/javascript"
    );

    private final static Map<String, String> STATIC_PATH_BY_FILE = Map.of(
            "html", "./templates",
            "css", "./static",
            "js", "./static"
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
            return FileIoUtils.loadFileFromClasspath(STATIC_PATH_BY_FILE.get(getExtension(path)) + path);
        } catch (IOException | URISyntaxException e) {
            throw new LoadFileException("[" + path + "] 파일 로드 실패", e);
        }
    }

    private String getExtension(String path) {
        String[] split = path.split("/");

        String resourceName = split[split.length - 1];

        String[] splited = resourceName.split("\\.");

        if (splited.length < 2) {
            return "";
        }

        return splited[splited.length - 1];
    }

}
