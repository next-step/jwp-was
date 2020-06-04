package webserver.processor;

import http.HttpRequest;
import http.HttpResponse;

import java.io.IOException;
import java.net.URISyntaxException;

public class ResourceProcessor implements Processor {
    @Override
    public boolean isMatch(HttpRequest httpRequest) {
        return false;
    }

    @Override
    public HttpResponse process(HttpRequest httpRequest) throws IOException, URISyntaxException {
        return null;
    }
}
