package webserver.handler;

import utils.FileIoUtils;
import webserver.Handler;
import webserver.ModelAndView;
import webserver.NotFoundResourceException;
import webserver.StaticLocationProvider;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;
import webserver.http.Status;

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

    public StaticFileHandler() {
        this(new StaticLocationProvider());
    }

    public StaticFileHandler(StaticLocationProvider staticLocationProvider) {
        this.staticLocationProvider = staticLocationProvider;
    }

    public ModelAndView handle(HttpRequest httpRequest, HttpResponse httpResponse) {
        try {
            handlerStaticResource(httpRequest, httpResponse);
        } catch (NotFoundResourceException | IOException e) {
            sendError(httpResponse, Status.NOT_FOUND, "not found " + httpRequest.getPath());
        } catch (URISyntaxException e) {
            sendError(httpResponse, Status.INTERNAL_SERVER_ERROR, "error has occurred");
        }

        return null;
    }

    private void handlerStaticResource(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException, URISyntaxException {
        String staticResourcePath = staticLocationProvider.getStaticResourcePath(httpRequest.getPath());

        byte[] bytes = FileIoUtils.loadFileFromClasspath(staticResourcePath);

        httpResponse.setBody(bytes);

        httpResponse.setContentType(getContentType(staticResourcePath));
    }

    private void sendError(HttpResponse httpResponse, Status status, String message) {
        try {
            httpResponse.sendError(status, message);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String getContentType(String resourcePath) {
        String extension = getExtension(resourcePath);

        return CONTENT_TYPE_BY_EXTENSION.get(extension);
    }
}
