package webserver.controller;

import java.util.Map;

import db.DataBase;
import http.HttpStatus;
import http.request.HttpRequest;
import http.response.HttpResponse;

public class LoginController implements PostController {

    @Override
    public HttpResponse doPost(HttpRequest request) {
        var params = request.getParameters();

        var userId = params.get("userId");

        var isLogined = DataBase.findUserById(userId)
            .filter(it -> it.canLogin(params.get("password")))
            .isPresent();

        request.getHttpSession()
            .setAttribute("isLogined", String.valueOf(isLogined));

        var httpResponse = createResponse(isLogined);
        httpResponse.putCookie(request.getHttpSession());

        return httpResponse;
    }

    private HttpResponse createResponse(boolean isLogined) {
        if (isLogined) {
            return new HttpResponse(HttpStatus.FOUND, Map.of("Location", "/index.html"));
        }
        return new HttpResponse(HttpStatus.FOUND, Map.of("Location", "/user/login_failed.html"));
    }
}
