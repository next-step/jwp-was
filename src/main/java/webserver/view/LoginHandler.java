package webserver.view;

import db.DataBase;
import model.User;
import webserver.AbstractRequestMappingHandler;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;

import java.io.IOException;

import static webserver.http.HttpHeaders.SET_COOKIE;

public class LoginHandler extends AbstractRequestMappingHandler {

    @Override
    public void doPost(HttpRequest request, HttpResponse response) throws IOException {
        User user = DataBase.findUserById(request.getRequestBodyParameter("userId"));

        if (isLoginSuccess(user, request.getRequestBodyParameter("password"))) {
            response.addHeader(SET_COOKIE, "logined=false; Path=/");
            response.response302Header("/user/login_failed.html");
        } else {
            response.addHeader(SET_COOKIE, "logined=true; Path=/");
            response.response302Header("/index.html");
        }
    }

    private boolean isLoginSuccess(User user, String password) {
        return user == null || !user.isPasswordMatch(password);
    }
}
