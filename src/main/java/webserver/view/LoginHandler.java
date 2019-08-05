package webserver.view;

import db.DataBase;
import model.User;
import webserver.AbstractRequestMappingHandler;
import webserver.http.request.HttpRequest;
import webserver.http.request.RequestBody;
import webserver.http.response.HttpResponse;

import java.io.IOException;

public class LoginHandler extends AbstractRequestMappingHandler {

    @Override
    public void process(HttpRequest request, HttpResponse response) throws IOException {
        User user = DataBase.findUserById(request.getParameter("userId"));

        if (isLoginSuccess(user, request.getParameter("password"))) {
            response.response302Header("/user/login_failed.html", false);
        } else {
            response.response302Header( "/index.html", true);
        }
    }

    private boolean isLoginSuccess(User user, String password) {
        return user == null || !user.isPasswordMatch(password);
    }
}
