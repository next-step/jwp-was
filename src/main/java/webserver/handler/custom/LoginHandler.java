package webserver.handler.custom;

import db.DataBase;
import http.request.Headers;
import http.request.QueryStrings;
import http.request.Request;
import http.response.*;
import model.User;
import org.apache.logging.log4j.util.Strings;
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
            return makeResponseWhenUserIsAuthenticated(headers);
        }

        return makeResponseWhenUserIsNotAuthenticated(headers);
    }

    @Override
    public String getUrl() {
        return this.url;
    }

    private Response makeResponseWhenUserIsAuthenticated(Map<String, String> headers)
            throws IOException, URISyntaxException {
        ResponseBody body = new ResponseBody(FileIoUtils.loadFileFromClasspath("./templates/index.html"));
        headers.put(HEADER_LOCATION, "/index.html");
        Response response = new Response(HttpStatus.FOUND, ContentType.HTML, new Headers(headers), body);
        response.addCookie(new Cookie("logined=true", "/", false));
        return response;
    }

    private Response makeResponseWhenUserIsNotAuthenticated(Map<String, String> headers) throws IOException, URISyntaxException {
        headers.put(HEADER_LOCATION, "/user/login_failed.html");
        ResponseBody body = new ResponseBody(FileIoUtils.loadFileFromClasspath("./templates/user/login_failed.html"));
        Response response = new Response(HttpStatus.FOUND, ContentType.HTML, new Headers(headers), body);
        response.addCookie(new Cookie("logined=false", Strings.EMPTY, false));
        return response;
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
