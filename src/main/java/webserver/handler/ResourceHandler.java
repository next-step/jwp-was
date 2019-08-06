package webserver.handler;

import webserver.http.HeaderKey;
import webserver.http.request.Request;
import webserver.http.response.Response;

public class ResourceHandler implements Handler {

    private static final String COMMA = ",";
    private static final int INDEX_OF_FIRST = 0;

    private final String prefix;

    public ResourceHandler(final String prefix) {
        this.prefix = prefix;
    }

    @Override
    public void handle(final Request request,
                       final Response response) throws Exception {
        final String accept = request.getHeader(HeaderKey.ACCEPT);
        final String contentType = accept.split(COMMA)[INDEX_OF_FIRST];

        response.forward(prefix + request.getPath(), contentType);
    }
}
