package webserver.controller;

import db.DataBase;
import model.User;
import webserver.HttpHeaders;
import webserver.exception.ApiException;
import webserver.request.HttpMethod;
import webserver.request.HttpRequest;
import webserver.request.Path;
import webserver.response.HttpResponse;
import webserver.response.HttpStatusCode;
import webserver.response.ResponseHeader;

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
        return HttpResponse.of(
                HttpStatusCode.FOUND,
                ResponseHeader.from(Map.of(
                        HttpHeaders.LOCATION, "/index.html",
                        HttpHeaders.SET_COOKIE, "logined=true; Path=/"
                ))
        );
    }

    private void validateUser(HttpRequest request) {
        User user = DataBase.findUserById(extractRequiredBody(request, "userId"));
        if (user == null || isNotMatchPassword(request, user)) {
            throw new ApiException(
                    HttpStatusCode.FOUND,
                    ResponseHeader.from(Map.of(
                            HttpHeaders.LOCATION, "/user/login_failed.html",
                            HttpHeaders.SET_COOKIE, "logined=false; Path=/")
                    )
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
