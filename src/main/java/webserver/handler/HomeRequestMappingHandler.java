package webserver.handler;

import webserver.http.HttpRequest;
import webserver.http.HttpResponse;
import webserver.resolver.ViewResolver;

import java.util.HashMap;
import java.util.Map;

public class HomeRequestMappingHandler extends RequestMappingHandler {

    public HomeRequestMappingHandler(ViewResolver viewResolver) {
        super(viewResolver);
    }

    @Override
    public HttpResponse doGet(HttpRequest httpRequest) throws Exception {
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", viewResolver.getContentType());
        byte[] body = viewResolver.resolve("/index.html");
        return HttpResponse.ok(headers, body);
    }

    @Override
    public String getRequestMapping() {
        return "/";
    }
}