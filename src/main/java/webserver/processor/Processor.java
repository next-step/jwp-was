package webserver.processor;

import http.HttpRequest;
import http.response.HttpResponse;

import java.io.IOException;

public interface Processor {
    boolean isMatch(HttpRequest httpRequest);
    void process(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException;
}
