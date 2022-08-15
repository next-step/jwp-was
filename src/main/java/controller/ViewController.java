package controller;

import model.HttpRequest;
import model.HttpResponse;
import model.ResponseBody;
import utils.FileIoUtils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Map;

public class ViewController implements Controller{

    private static final String HTML_CLASS_PATH = "./templates";
    private static final String CLASS_Path = "./static";

    private static final Map<String, String> extensionToContentType = Map.of(
            "css", "text/css",
            "html", "text/html",
            "js", "application/javascript",
            "ico", "image/x-icon");

    @Override
    public void service(HttpRequest request, HttpResponse response) throws IOException, URISyntaxException {
        final String requestPath = request.getRequestLine().getFullRequestPath();
        final String extension = request.getRequestLine().getExtension();

        if (isHtmlClassPath(extension)) {
            response.forward(
                    new ResponseBody(FileIoUtils.loadFileFromClasspath(HTML_CLASS_PATH + requestPath))
                    , extensionToContentType.get(extension));
            return;
        }
        response.forward(
                new ResponseBody(FileIoUtils.loadFileFromClasspath(CLASS_Path + requestPath))
                , extensionToContentType.get(extension));
    }

    private boolean isHtmlClassPath(String extension) {
        return extension.equals("html") || extension.equals("ico");
    }
}
