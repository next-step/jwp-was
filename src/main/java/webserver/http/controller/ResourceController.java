package webserver.http.controller;

import utils.FileIoUtils;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;

import java.io.IOException;
import java.net.URISyntaxException;

public class ResourceController extends AbstractController {

    @Override
    protected void doGet(final HttpRequest request, final HttpResponse response) throws IOException {
        try {
            this.setResource(request, response);
            response.responseOK();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    private void setResource(final HttpRequest request, final HttpResponse response) throws IOException, URISyntaxException {
        final String requestPath = request.getPath();

        if (requestPath.endsWith(".html")) {
            final byte[] body = FileIoUtils.loadFileFromClasspath("templates" + requestPath);
            response.setBody(body);
            response.setContentType("text/html;charset=utf-8");

        } else if (requestPath.endsWith(".css")) {
            final byte[] body = FileIoUtils.loadFileFromClasspath("static" + requestPath);
            response.setBody(body);
            response.setContentType("text/css;charset=utf-8");

        } else if (requestPath.endsWith(".js")) {
            final byte[] body = FileIoUtils.loadFileFromClasspath("static" + requestPath);
            response.setBody(body);
            response.setContentType("text/javascript;charset=utf-8");

        } else if (requestPath.contains("/fonts/")) {
            final byte[] body = FileIoUtils.loadFileFromClasspath("static" + requestPath);
            response.setBody(body);
            final String extension = this.getExtensionFrom(requestPath);
            response.setContentType("font/" + extension + ";charset=utf-8");

        } else if (requestPath.equals("/favicon.ico")) {
            final byte[] body = FileIoUtils.loadFileFromClasspath("templates" + requestPath);
            response.setBody(body);
            response.setContentType("image/x-icon;charset=utf-8");

        } else {
            throw new IllegalArgumentException("Not supported resource - content path: " + requestPath);
        }
    }

    private String getExtensionFrom(final String contentPath) {
        return contentPath.substring(
                contentPath.lastIndexOf(".") + 1
        );
    }
}
