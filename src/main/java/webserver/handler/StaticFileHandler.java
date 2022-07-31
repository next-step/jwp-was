package webserver.handler;

import utils.FileIoUtils;
import webserver.Handler;
import webserver.LoadFileException;
import webserver.ModelAndView;
import webserver.StaticLocationProvider;
import webserver.http.Request;
import webserver.http.Response;

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

    private final StaticLocationProvider staticLocationProvider;

    public StaticFileHandler(StaticLocationProvider staticLocationProvider) {
        this.staticLocationProvider = staticLocationProvider;
    }

    public ModelAndView handle(Request request, Response response) {
        String path = request.getPath();

        byte[] bytes = loadFile(path);

        response.setBody(bytes);
        response.setContentType(CONTENT_TYPE_BY_EXTENSION.get(getExtension(path)));
        return null;
    }

    private byte[] loadFile(String path) {
        try {
            String staticLocation = staticLocationProvider.getStaticLocation(path);
            return FileIoUtils.loadFileFromClasspath(staticLocation + path);
        } catch (IOException | URISyntaxException e) {
            throw new LoadFileException("[" + path + "] 파일 로드 실패", e);
        }
    }
}
