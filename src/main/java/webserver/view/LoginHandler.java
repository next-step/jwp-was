package webserver.view;

import db.DataBase;
import model.User;
import webserver.AbstractRequestMappingHandler;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;

import java.io.IOException;

import static webserver.http.HttpHeaders.LOCATION;
import static webserver.http.HttpHeaders.SET_COOKIE;

public class LoginHandler extends AbstractRequestMappingHandler {

    @Override
    public void process(HttpRequest request, HttpResponse response) throws IOException {
        User user = DataBase.findUserById(request.getParameter("userId"));

        if (isLoginSuccess(user, request.getParameter("password"))) {
            response.addHeader(LOCATION, "/user/login_failed.html");
            response.addHeader(SET_COOKIE, "logined=false; Path=/");
        } else {
            response.addHeader(LOCATION, "/index.html");
            response.addHeader(SET_COOKIE, "logined=true; Path=/");
        }

        response.response302Header();
    }

    private boolean isLoginSuccess(User user, String password) {
        return user == null || !user.isPasswordMatch(password);
    }
}
