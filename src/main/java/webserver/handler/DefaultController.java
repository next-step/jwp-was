package webserver.handler;

import webserver.request.HttpRequest;
import webserver.response.HttpResponse;

public class DefaultController implements Controller {

    @Override
    public HttpResponse handle(final HttpRequest request) {
        return null;
    }
}
