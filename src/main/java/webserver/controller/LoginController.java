package webserver.controller;

import db.DataBase;
import enums.HttpMethod;
import enums.HttpStatusCode;
import java.util.Map;
import java.util.Objects;
import model.User;
import webserver.Cookie;
import webserver.HttpHeader;
import webserver.request.HttpRequest;
import webserver.request.RequestBody;
import webserver.response.HttpResponse;

public class LoginController implements Controller {
    static final String EXECUTABLE_PATH = "/user/login";
    static final HttpMethod EXECUTABLE_METHOD = HttpMethod.POST;
    static final String LOGIN_SUCCESS_REDIRECT_URL = "/index.html";
    static final String LOGIN_FAIL_REDIRECT_URL = "/user/login_failed.html";

    @Override
    public boolean canExecute(HttpRequest httpRequest) {
        return httpRequest.getPath().equals(EXECUTABLE_PATH) &&
                httpRequest.getMethod().equals(EXECUTABLE_METHOD);
    }

    @Override
    public HttpResponse execute(HttpRequest httpRequest) throws Exception {
        RequestBody body = httpRequest.getBody();

        User user = login(body.getValue("userId"), body.getValue("password"));

        if (user == null) {
            return fail();
        }

        return success();
    }

    private User login(String userId, String password) {
        User user = DataBase.findUserById(userId);

        if (user != null && user.equalsPassword(password)) {
            return user;
        }

        return null;
    }

    private HttpResponse success() throws Exception {
        HttpHeader header = new HttpHeader(Map.of("Location", LOGIN_SUCCESS_REDIRECT_URL));
        Cookie cookie = new Cookie("logined", "true", "/");
        HttpResponse response = new HttpResponse(HttpStatusCode.FOUND, header);
        response.addCookie(cookie);

        return response;
    }

    private HttpResponse fail() throws Exception {
        HttpHeader header = new HttpHeader(Map.of("Location", LOGIN_FAIL_REDIRECT_URL));
        Cookie cookie = new Cookie("logined", "false", "/");
        HttpResponse response = new HttpResponse(HttpStatusCode.FOUND, header);
        response.addCookie(cookie);

        return response;
    }
}
