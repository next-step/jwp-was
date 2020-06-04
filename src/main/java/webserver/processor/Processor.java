package webserver.processor;

import http.HttpRequest;
import http.HttpResponse;

import java.io.IOException;
import java.net.URISyntaxException;

public interface Processor {
    boolean isMatch(HttpRequest httpRequest);
    HttpResponse process(HttpRequest httpRequest) throws IOException, URISyntaxException;
}
