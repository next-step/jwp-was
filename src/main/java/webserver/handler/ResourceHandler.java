package webserver.handler;

import utils.FileIoUtils;
import webserver.http.response.HttpResponse;
import webserver.http.request.HttpRequest;

public class ResourceHandler implements Handler {

    private final String prefix;

    public ResourceHandler(final String prefix) {
        this.prefix = prefix;
    }

    @Override
    public void handle(final HttpRequest request,
                       final HttpResponse response) throws Exception {
        final String accept = request.getHeader("Accept");
        final String contentType = accept.split(",")[0];

        response.addHeader("Content-Type", contentType);

        response.ok(FileIoUtils.loadFileFromClasspath(prefix + request.getPath()));
    }
}
