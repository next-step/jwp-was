package webserver.handler;

import webserver.http.HttpRequest;
import webserver.http.HttpResponse;
import webserver.resolver.ViewResolver;

import java.util.HashMap;
import java.util.Map;

public class TemplateResourceHandler implements Handler {

    private static final String HTML_EXTENSION = ".html";

    private ViewResolver viewResolver;

    public TemplateResourceHandler(ViewResolver viewResolver) {
        this.viewResolver = viewResolver;
    }

    @Override
    public HttpResponse doHandle(HttpRequest httpRequest) throws Exception {
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", viewResolver.getContentType());
        byte[] body = viewResolver.resolve(httpRequest.getPath());
        return HttpResponse.ok(headers, body);
    }

    @Override
    public boolean canHandle(HttpRequest httpRequest) {
        return httpRequest.getPath().endsWith(HTML_EXTENSION);
    }
}