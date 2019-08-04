package webserver.controller;

import db.DataBase;
import model.User;
import webserver.RequestMappingHandler;
import webserver.http.request.HttpRequest;
import webserver.http.request.RequestBody;
import webserver.http.response.HttpResponse;

import java.io.IOException;

public class LoginHandler implements RequestMappingHandler {

    @Override
    public void handleRequest(HttpRequest request, HttpResponse response) throws IOException {
        RequestBody requestBody = request.getRequestBody();

        User user = DataBase.findUserById(requestBody.getValue("userId"));

        if (isLoginSuccess(user, requestBody.getValue("password"))) {
            response.response302Header("/user/login_failed.html", false);
        } else {
            response.response302Header( "/index.html", true);
        }
    }

    private boolean isLoginSuccess(User user, String password) {
        return user == null || !user.isPasswordMatch(password);
    }
}
