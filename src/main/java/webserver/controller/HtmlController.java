package webserver.controller;

import http.common.ContentType;
import http.request.HttpRequest;
import http.response.HttpResponse;
import utils.FileIoUtils;

public class HtmlController implements Controller {

    private HtmlController() {
    }

    private static class Singleton {
        private static final HtmlController instance = new HtmlController();
    }

    public static HtmlController getInstance() {
        return HtmlController.Singleton.instance;
    }

    @Override
    public void service(HttpRequest httpRequest, HttpResponse httpResponse) {
        final String path = httpRequest.getPath();
        final byte[] html = FileIoUtils.loadFileFromClasspath("./templates/" + path);

        httpResponse.setContentType(ContentType.TEXT_HTML_UTF_8);
        httpResponse.setBody(html);
    }
}
