package webserver.handler;

import utils.FileIoUtils;
import webserver.Handler;
import webserver.LoadFileException;
import webserver.RequestMappingInfo;
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
    public RequestMappingInfo getMappingInfo() {
        return new RequestMappingInfo("/**", HttpMethod.GET);
    }

    public void handle(Request request, Response response) {
        String path = request.getPath();

        byte[] bytes = loadFile(path);

        response.setBody(bytes);
        response.setContentType(CONTENT_TYPE_BY_EXTENSION.get(getExtension(path)));
    }

    private byte[] loadFile(String path) {
        try {
            String staticLocation = staticLocationConfig.getStaticLocation(path);
            return FileIoUtils.loadFileFromClasspath(staticLocation + path);
        } catch (IOException | URISyntaxException e) {
            throw new LoadFileException("[" + path + "] 파일 로드 실패", e);
        }
    }
}
