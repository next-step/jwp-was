package webserver.handler;

import db.DataBase;
import model.User;
import webserver.http.Cookies;
import webserver.http.CustomCookie;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;
import webserver.resolver.ViewResolver;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

        Boolean logined = isLogined(httpRequest.getBody());
        String viewName = getViewName(logined);

        Cookies cookies = Cookies.newInstance();
        cookies.add(CustomCookie.LOGINED, logined.toString());
        cookies.add(Cookies.PATH, "/");
        headers.put("Set-Cookie", cookies.joinToString());
        headers.put("Location", viewName);
        byte[] body = viewResolver.resolve(viewName);
        return HttpResponse.redirect(headers, body);
    }

    private Boolean isLogined(Map<String, String> requestBody) {
        User user = DataBase.findUserById(requestBody.get("userId"));
        return user.matchPassword(requestBody.get("password"));
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