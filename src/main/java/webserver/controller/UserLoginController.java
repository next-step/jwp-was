package webserver.controller;

import db.DataBase;
import model.User;
import webserver.http.HttpHeaders;
import webserver.exception.ApiException;
import webserver.http.request.HttpMethod;
import webserver.http.request.HttpRequest;
import webserver.http.request.Path;
import webserver.http.response.HttpResponse;
import webserver.http.response.HttpStatusCode;
import webserver.http.response.ResponseHeader;

import java.util.Collections;
import java.util.Map;

public class UserLoginController implements Controller {

    private static final Path USER_LOGIN_PATH = Path.from("/user/login");

    @Override
    public boolean isMatch(HttpRequest request) {
        return request.matchMethodAndPath(HttpMethod.POST, USER_LOGIN_PATH);
    }

    @Override
    public HttpResponse execute(HttpRequest request) {
        return login(request);
    }

    private HttpResponse login(HttpRequest request) {
        validateUser(request);
        return HttpResponse.sendRedirect("/index.html",
                ResponseHeader.from(Collections.singletonMap(HttpHeaders.SET_COOKIE, "logined=true; Path=/")));
    }

    private void validateUser(HttpRequest request) {
        User user = DataBase.findUserById(extractRequiredBody(request, "userId"));
        if (user == null || isNotMatchPassword(request, user)) {
            throw new ApiException(
                    HttpStatusCode.FOUND,
                    ResponseHeader.from(Map.of(
                            HttpHeaders.LOCATION, "/user/login_failed.html",
                            HttpHeaders.SET_COOKIE, "logined=false; Path=/"
                    ))
            );
        }
    }

    private boolean isNotMatchPassword(HttpRequest request, User user) {
        return !extractRequiredBody(request, "password").equals(user.getPassword());
    }

    private String extractRequiredBody(HttpRequest request, String property) {
        return request.bodyValue(property)
                .orElseThrow(() -> new ApiException(HttpStatusCode.BAD_REQUEST));
    }
}
