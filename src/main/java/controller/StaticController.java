package controller;

import static controller.UserListController.CONTENT_TYPE;

import java.io.IOException;
import java.net.URISyntaxException;
import model.request.HttpRequest;
import model.response.HttpResponse;
import model.response.ResponseBody;
import utils.FileIoUtils;

public class StaticController extends AbstractController {
    private static final String HTML_CLASS_PATH = "./templates";
    private static final String CLASS_PATH = "./static";

    @Override
    public void service(HttpRequest request, HttpResponse response) throws IOException, URISyntaxException {
        String path = request.getHttpPath();
        if (isHtmlClassPath(path)) {
            byte[] body = FileIoUtils.loadFileFromClasspath(HTML_CLASS_PATH + path);
            HttpResponse.forward(new ResponseBody(body), CONTENT_TYPE);
            return;
        }

        byte[] body = FileIoUtils.loadFileFromClasspath(CLASS_PATH + path);
        HttpResponse.forward(new ResponseBody(body), CONTENT_TYPE);
    }

    private boolean isHtmlClassPath(String path) {
        return path.contains("html") || path.contains("ico");
    }
}
