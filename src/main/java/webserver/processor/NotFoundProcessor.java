package webserver.processor;

import http.HttpRequest;
import http.HttpResponse;
import http.StatusCode;

public class NotFoundProcessor implements Processor {
    @Override
    public boolean isMatch(final HttpRequest httpRequest) {
        return true;
    }

    @Override
    public void process(final HttpRequest httpRequest, final HttpResponse httpResponse) {
        httpResponse.updateStatus(StatusCode.NOT_FOUND);
    }
}
