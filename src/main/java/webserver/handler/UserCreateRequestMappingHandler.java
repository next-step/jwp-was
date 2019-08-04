package webserver.handler;

import webserver.http.HttpRequest;
import webserver.http.HttpResponse;
import webserver.resolver.ViewResolver;

import java.util.HashMap;
import java.util.Map;

public class UserCreateRequestMappingHandler extends RequestMappingHandler {

    public UserCreateRequestMappingHandler(ViewResolver viewResolver) {
        super(viewResolver);
    }

    @Override
    protected HttpResponse doGet(HttpRequest httpRequest) throws Exception {
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", viewResolver.getContentType());
        byte[] body = viewResolver.resolve("/user/form.html");
        return HttpResponse.ok(headers, body);
    }

    @Override
    protected String getRequestMapping() {
        return "/user/create";
    }
}