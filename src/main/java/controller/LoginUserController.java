package controller;

import db.DataBase;
import model.User;
import webserver.http.request.HttpRequest;
import webserver.http.request.body.HttpRequestBody;
import webserver.http.response.HttpResponse;
import webserver.http.response.start_line.StatusCode;

import java.util.HashMap;
import java.util.Map;

public class LoginUserController implements Controller {

    @Override
    public void process(HttpRequest request, HttpResponse response) {
        HttpRequestBody httpRequestBody = request.getHttpRequestBody();

        Map<String, String> body = parseBody(httpRequestBody.getBodyValue());

        String userId = body.get("userId");
        String password = body.get("password");

        User user = DataBase.findUserById(userId);

        if (user == null || !user.matchPassword(password)) {
            response.addHeader("Set-Cookie", "logined=false");
            response.redirect(StatusCode.FOUND, "/user/login_failed.html");
            return;
        }

        response.addHeader("Set-Cookie", "logined=true");
        response.redirect(StatusCode.FOUND, "/index.html");
    }

    private Map<String, String> parseBody(final String bodyValue) {
        Map<String, String> body = new HashMap<>();

        String[] split = bodyValue.split("&");

        for (String value : split) {
            String[] keyValue = value.split("=");
            body.put(keyValue[0], keyValue[1]);
        }

        return body;
    }
}
