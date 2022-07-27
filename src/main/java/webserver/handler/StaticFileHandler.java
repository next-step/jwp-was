package webserver.handler;

import utils.FileIoUtils;
import webserver.LoadFileException;
import webserver.Handler;
import webserver.config.StaticLocationConfig;
import webserver.http.*;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Map;

import static utils.FileNameUtils.getExtension;

public class StaticFileHandler implements Handler {

    private final static Map<String, String> CONTENT_TYPE_BY_EXTENSION = Map.of(
            "html", "text/html",
            "css", "text/css",
            "js", "application/javascript"
    );

    private final StaticLocationConfig staticLocationConfig = new StaticLocationConfig();

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
        response.setContentType(CONTENT_TYPE_BY_EXTENSION.get(getExtension(path)));
        return response;
    }

    private byte[] loadFile(String path) {
        try {
            String extension = getExtension(path);
            return FileIoUtils.loadFileFromClasspath(staticLocationConfig.getStaticLocation(extension) + path);
        } catch (IOException | URISyntaxException e) {
            throw new LoadFileException("[" + path + "] 파일 로드 실패", e);
        }
    }

}
