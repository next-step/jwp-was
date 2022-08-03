package controller;

import model.HttpRequest;
import model.HttpResponse;
import utils.FileIoUtils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Map;

public class ViewController implements Controller{

    private static final String HTML_CLASS_Path = "./templates";
    private static final String CLASS_Path = "./static";

    Map<String, String> path = Map.of(
            "css", "text/css",
            "html", "text/html",
            "js", "application/javascript");

    @Override
    public HttpResponse process(HttpRequest request) throws IOException, URISyntaxException {
        final String requestPath = request.getRequestLine().getRequestPath();
        final String extension = request.getRequestLine().getExtension();

        if (extension.equals("html")) {
            return HttpResponse.successView(
                    FileIoUtils.loadFileFromClasspath(HTML_CLASS_Path + requestPath), path.get(extension));
        }
        return HttpResponse.successView(
                FileIoUtils.loadFileFromClasspath(CLASS_Path + requestPath), path.get(extension));
    }
}
