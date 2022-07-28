package webserver.handler;

import webserver.request.HttpRequest;
import webserver.request.RequestLine;
import webserver.response.HttpResponse;

public class DefaultController implements Controller {

    @Override
    public HttpResponse handle(final HttpRequest request) {
        final RequestLine requestLine = request.getRequestLine();

        return HttpResponse.ok(requestLine.getLocation());
    }
}
