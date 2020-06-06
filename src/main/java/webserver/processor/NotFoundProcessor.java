package webserver.processor;

import http.HttpRequest;
import http.HttpResponse;
import http.StatusCode;

public class NotFoundProcessor implements Processor {
    @Override
    public boolean isMatch(HttpRequest httpRequest) {
        return false;
    }

    @Override
    public void process(HttpRequest httpRequest, HttpResponse httpResponse) {
        httpResponse.updateStatus(StatusCode.NOT_FOUND);
    }
}
