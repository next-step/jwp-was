package servlet;

import controller.UserController;
import http.HeaderName;
import http.HttpRequest;
import http.HttpResponse;
import utils.FileIoUtils;

import java.io.IOException;
import java.net.URISyntaxException;

public class DispatcherServlet {

    public static void handle(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException, URISyntaxException {

        if (httpRequest.isGetRequest()) {
            byte[] body = new byte[10];

            if (httpRequest.pathEndsWith(".html")) {
                body = FileIoUtils.loadFileFromClasspath("./templates" + httpRequest.getPath());
            } else if (httpRequest.matchPath("/user/list")) {
                UserController.findAll(httpRequest, httpResponse);
            } else {
                body = FileIoUtils.loadFileFromClasspath("./static" + httpRequest.getPath());
            }

            httpResponse.response200Header(httpRequest.getHeaderValue(HeaderName.ACCEPT).get().split(",")[0], body.length);
            httpResponse.responseBody(body);

        } else if (httpRequest.isPostRequest()) {

            if (httpRequest.matchPath("/user/create")) {
                UserController.join(httpRequest, httpResponse);
            } else if (httpRequest.matchPath("/user/login")) {
                UserController.login(httpRequest, httpResponse);
            }
        }

    }
}
