package webserver.controller;

import enums.HttpStatusCode;
import java.io.IOException;
import java.net.URISyntaxException;
import utils.FileIoUtils;
import webserver.Cookie;
import webserver.HttpHeader;
import webserver.request.HttpRequest;
import webserver.response.HttpResponse;

public class ViewController implements Controller {
    private static final String TEMPLATE_PATH = "./templates";
    private static final String STATIC_PATH = "./static";

    @Override
    public boolean canExecute(HttpRequest httpRequest) {
        return FileIoUtils.exists(TEMPLATE_PATH + httpRequest.getPath()) ||
                FileIoUtils.exists(STATIC_PATH + httpRequest.getPath());
    }

    @Override
    public HttpResponse execute(HttpRequest httpRequest) throws Exception {
        String path = httpRequest.getPath();
        String[] split = path.split("\\.");
        String extension = split[split.length - 1];

        if (extension.equals("html") || extension.equals("ico")) {
            return templateFile(path);
        }

        return styleFile(path);
    }

    private HttpResponse templateFile(String path) throws IOException, URISyntaxException {
        return new HttpResponse(
                HttpStatusCode.OK,
                FileIoUtils.loadFileFromClasspath(TEMPLATE_PATH + path)
        );
    }

    private HttpResponse styleFile(String path) throws IOException, URISyntaxException {
        return new HttpResponse(
                HttpStatusCode.OK,
                FileIoUtils.loadFileFromClasspath(STATIC_PATH + path)
        );
    }
}
