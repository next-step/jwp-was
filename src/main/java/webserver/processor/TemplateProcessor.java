package webserver.processor;

import http.HttpRequest;
import http.HttpResponse;

public class TemplateProcessor implements Processor {

    @Override
    public boolean isMatch(final HttpRequest httpRequest) {
        return httpRequest.getPath()
                .endsWith(".html");
    }

    @Override
    public HttpResponse process(final HttpRequest httpRequest) {
        return null;
    }
}
