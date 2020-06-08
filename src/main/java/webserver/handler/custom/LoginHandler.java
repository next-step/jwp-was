package webserver.handler.custom;

import db.DataBase;
import http.request.Headers;
import http.request.QueryStrings;
import http.request.Request;
import http.response.*;
import model.User;
import utils.FileIoUtils;
import webserver.handler.Handler;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

public class LoginHandler implements Handler {
    private static final String HEADER_SET_COOKIE = "Set-Cookie";
    private static final String HEADER_LOCATION = "Location";
    private static final String KEY_USER_ID = "userId";
    private static final String KEY_PASSWORD = "password";

    private String url;

    public LoginHandler(String url) {
        this.url = url;
    }

    @Override
    public boolean isSameUrl(String url) {
        return this.url.equals(url);
    }

    @Override
    public Response work(Request request) throws IOException, URISyntaxException {
        Map<String, String> queryStrings = QueryStrings.parseQueryStrings(request.getRequestBody().getBody());
        User user = getUser(queryStrings);
        Map<String, String> headers = new HashMap<>();

        if (isAuthenticatedUser(queryStrings, user)) {
            ResponseBody body = new ResponseBody(FileIoUtils.loadFileFromClasspath("./templates/index.html"));
            request.addCookie(new Cookie("logined=true", "/", false));
            headers.put(HEADER_LOCATION, "/index.html");
            new Response(HttpStatus.FOUND, ContentType.HTML, new Headers(headers), body);
        }

        headers.put(HEADER_SET_COOKIE, "logined=false");
        headers.put(HEADER_LOCATION, "/user/login_failed.html");
        ResponseBody body = new ResponseBody(FileIoUtils.loadFileFromClasspath("./templates/user/login_failed.html"));
        return new Response(HttpStatus.FOUND, ContentType.HTML, new Headers(headers), body);
    }

    @Override
    public String getUrl() {
        return this.url;
    }

    private User getUser(Map<String, String> queryStrings) {
        try {
            return DataBase.findUserById(queryStrings.get(KEY_USER_ID));
        } catch (Exception e) {
            return new User();
        }
    }

    private boolean isAuthenticatedUser(Map<String, String> queryStrings, User user) {
        String password = queryStrings.get(KEY_PASSWORD);
        return user.getPassword().equals(password);
    }
}
