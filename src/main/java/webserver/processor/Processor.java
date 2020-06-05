package webserver.processor;

import http.HttpRequest;
import http.HttpResponse;

public interface Processor {
    boolean isMatch(HttpRequest httpRequest);
    HttpResponse process(HttpRequest httpRequest);
}
