package webserver.controller;

import db.DataBase;
import exception.NotFoundException;
import http.request.Protocol;
import model.User;
import webserver.HttpHeader;
import webserver.request.HttpRequest;
import webserver.response.HttpResponse;
import webserver.response.HttpStatusCode;
import webserver.response.StatusLine;

import java.util.Map;

public class UserLoginController implements Controller {

    @Override
    public HttpResponse service(HttpRequest request) {
        return login(request);
    }

    private HttpResponse login(HttpRequest request) {
        if (isValidUserInfo(request)) {
            return HttpResponse.of(
                    StatusLine.of(Protocol.from("HTTP/1.1"), HttpStatusCode.FOUND),
                    HttpHeader.from(Map.of(
                            HttpHeader.LOCATION, "/index.html",
                            HttpHeader.SET_COOKIE, "logined=true; Path=/"))
            );
        }
        return HttpResponse.of(
                StatusLine.of(Protocol.from("HTTP/1.1"), HttpStatusCode.FOUND),
                HttpHeader.from(Map.of(
                        HttpHeader.LOCATION, "/user/login_failed.html",
                        HttpHeader.SET_COOKIE, "logined=false; Path=/"))
        );
    }

    private boolean isValidUserInfo(HttpRequest request) {
        User user = DataBase.findUserById(extractRequiredBody(request, "userId"));
        return user != null && checkPasswordMatching(request, user);
    }

    private boolean checkPasswordMatching(HttpRequest request, User user) {
        return extractRequiredBody(request, "password").equals(user.getPassword());
    }

    private String extractRequiredBody(HttpRequest request, String key) {
        return request.getBody(key).orElseThrow(() -> new NotFoundException(HttpStatusCode.BAD_REQUEST));
    }
}
