package webserver.controller;

import http.request.HttpRequest;
import http.response.HttpResponse;
import utils.FileIoUtils;

import java.io.IOException;
import java.net.URISyntaxException;

public class StylesheetController implements Controller {

    private StylesheetController() {}

    private static class Singleton {
        private static final StylesheetController instance = new StylesheetController();
    }

    public static StylesheetController getInstance() {
        return StylesheetController.Singleton.instance;
    }

    @Override
    public void service(HttpRequest httpRequest, HttpResponse httpResponse) {
        String path = httpRequest.getPath();
        try {
            byte[] stylesheet = FileIoUtils.loadFileFromClasspath("./static/" + path);
            httpResponse.response200CSS(stylesheet);
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }
}
