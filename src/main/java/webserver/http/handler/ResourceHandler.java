package webserver.http.handler;

import utils.FileIoUtils;
import webserver.HttpResponse;
import webserver.http.HttpRequest;

public class ResourceHandler implements Handler {

    private final String prefix;

    public ResourceHandler(final String prefix) {
        this.prefix = prefix;
    }

    @Override
    public void handle(final HttpRequest request,
                       final HttpResponse response) throws Exception {
        response.ok(FileIoUtils.loadFileFromClasspath(prefix + request.getPath()));
    }
}
