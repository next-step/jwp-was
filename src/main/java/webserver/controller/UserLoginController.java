package webserver.controller;

import db.DataBase;
import model.User;
import webserver.exception.ApiException;
import webserver.http.HttpHeaders;
import webserver.http.HttpSession;
import webserver.http.Session;
import webserver.http.SessionConfig;
import webserver.http.SessionStorage;
import webserver.http.request.HttpMethod;
import webserver.http.request.HttpRequest;
import webserver.http.request.Path;
import webserver.http.response.HttpResponse;
import webserver.http.response.HttpStatusCode;
import webserver.http.response.ResponseCookie;
import webserver.http.response.ResponseHeader;

import java.util.Map;

public class UserLoginController implements Controller {

    private static final Path USER_LOGIN_PATH = Path.from("/user/login");

    private final SessionStorage sessionStorage;

    public UserLoginController(SessionStorage sessionStorage) {
        this.sessionStorage = sessionStorage;
    }

    @Override
    public boolean isMatch(HttpRequest request) {
        return request.matchMethodAndPath(HttpMethod.POST, USER_LOGIN_PATH);
    }

    @Override
    public HttpResponse execute(HttpRequest request) {
        return login(request);
    }

    private HttpResponse login(HttpRequest request) {
        User user = DataBase.findUserById(extractRequiredBody(request, "userId"));
        validateUser(request, user);
        return HttpResponse.sendRedirect("/index.html",
                ResponseHeader.from(Map.of(
                        HttpHeaders.SET_COOKIE, ResponseCookie.of(SessionConfig.sessionCookieName(), saveUserSession(request, user).getId())
                ))
        );
    }

    private Session saveUserSession(HttpRequest request, User user) {
        return request.session()
                .map(session -> {
                    session.setAttribute("user", user);
                    return session;
                }).orElseGet(() -> {
                    Session session = HttpSession.from(Map.of("user", user));
                    sessionStorage.add(session);
                    return session;
                });
    }

    private void validateUser(HttpRequest request, User user) {
        if (user == null || isNotMatchPassword(request, user)) {
            throw new ApiException(
                    HttpStatusCode.FOUND,
                    ResponseHeader.from(Map.of(
                            HttpHeaders.LOCATION, "/user/login_failed.html",
                            HttpHeaders.SET_COOKIE, ResponseCookie.of("logined", "false")
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
