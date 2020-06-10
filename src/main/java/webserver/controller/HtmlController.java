package webserver.controller;

import http.request.HttpRequest;
import http.response.HttpResponse;
import utils.FileIoUtils;

import java.io.IOException;
import java.net.URISyntaxException;

public class HtmlController implements Controller {

    private HtmlController() {}

    private static class Singleton {
        private static final HtmlController instance = new HtmlController();
    }

    public static HtmlController getInstance() {
        return HtmlController.Singleton.instance;
    }

    @Override
    public void service(HttpRequest httpRequest, HttpResponse httpResponse) {
        final String path = httpRequest.getPath();
        try {
            final byte[] html = FileIoUtils.loadFileFromClasspath("./templates/" + path);
            httpResponse.response200HTML(html);
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }
}
