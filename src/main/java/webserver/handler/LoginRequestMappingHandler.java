package webserver.handler;

import db.DataBase;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;
import webserver.resolver.ViewResolver;

import java.util.*;
import java.util.stream.Collectors;

public class LoginRequestMappingHandler extends RequestMappingHandler {

    private static final String REQUEST_MAPPING = "/user/login";
    private static final List<String> REQUEST_MAPPINGS = Arrays.asList(REQUEST_MAPPING, REQUEST_MAPPING + ".html");

    public LoginRequestMappingHandler(ViewResolver viewResolver) {
        super(viewResolver);
    }

    @Override
    protected HttpResponse doGet(HttpRequest httpRequest) throws Exception {
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", viewResolver.getContentType());

        byte[] body = viewResolver.resolve(httpRequest.getPath());
        return HttpResponse.ok(headers, body);
    }

    @Override
    protected HttpResponse doPost(HttpRequest httpRequest) throws Exception {
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", viewResolver.getContentType());

        boolean logined = isLogined(httpRequest.getBody());
        String viewName = getViewName(logined);

        Map<String, Object> cookie = new LinkedHashMap<>();
        cookie.put("logined", logined);
        cookie.put("Path", "/");
        String responseCookie = cookie.entrySet().stream()
                .map(entry -> String.format("%s=%s", entry.getKey(), entry.getValue()))
                .collect(Collectors.joining("; "));
        headers.put("Set-Cookie", responseCookie);
        headers.put("Location", viewName);
        byte[] body = viewResolver.resolve(viewName);
        return HttpResponse.redirect(headers, body);
    }

    private boolean isLogined(Map<String, String> requestBody) {
        return DataBase.findAll().stream()
                .anyMatch(user -> user.login(
                        requestBody.get("userId"),
                        requestBody.get("password")
                ));
    }

    private String getViewName(boolean logined) {
        if (logined) {
            return "/index.html";
        }
        return "/user/login_failed.html";
    }

    @Override
    public boolean canHandle(HttpRequest httpRequest) {
        return REQUEST_MAPPINGS.stream()
                .anyMatch(requestMapping -> requestMapping.equals(httpRequest.getPath()));
    }

    @Override
    protected String getRequestMapping() {
        return REQUEST_MAPPING;
    }
}