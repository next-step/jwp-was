package webserver.http.controller;

import db.DataBase;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.http.domain.HttpRequest;
import webserver.http.domain.HttpResponse;
import webserver.http.domain.RequestBody;

public class LoginController implements Controller {

    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Override
    public void execute(HttpRequest httpRequest, HttpResponse httpResponse) {
        RequestBody requestBody = httpRequest.requestBody();

        String userId = requestBody.body("userId");
        User user = DataBase.findUserById(userId);

        if (user != null && user.samePassword(requestBody.body("password"))) {
            httpResponse.setLoginCookie(true, "/");
            httpResponse.sendRedirect("/index.html");
            return;
        }

        httpResponse.setLoginCookie(false, "/");
        httpResponse.sendRedirect("/user/login_failed.html");
    }
}
