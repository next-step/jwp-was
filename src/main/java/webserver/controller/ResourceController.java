package webserver.controller;

import webserver.http.HeaderName;
import webserver.http.request.Request;
import webserver.http.response.Response;

public class ResourceController extends AbstractController {

    private static final String COMMA = ",";
    private static final int INDEX_OF_FIRST = 0;

    private final String prefix;

    public ResourceController(final String prefix) {
        this.prefix = prefix;
    }

    @Override
    protected void doGet(final Request request,
                         final Response response) throws Exception {
        final String accept = request.getHeader(HeaderName.ACCEPT);
        final String contentType = accept.split(COMMA)[INDEX_OF_FIRST];

        response.forward(prefix + request.getPath(), contentType);
    }
}
