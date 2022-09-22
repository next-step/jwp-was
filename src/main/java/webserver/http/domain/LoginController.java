package webserver.http.domain;

import db.DataBase;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class LoginController implements Controller {

    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Override
    public void execute(HttpRequest httpRequest, HttpResponse httpResponse) {
        RequestBody requestBody = httpRequest.requestBody();

        String userId = requestBody.body("userId");
        User user = DataBase.findUserById(userId);

        if (user != null && user.samePassword(requestBody.body("password"))) {
            httpResponse.addHeader("Set-Cookie", "logined=true; Path=/ \r\n");
            httpResponse.sendRedirect("/index.html");
        }

        httpResponse.addHeader("Set-Cookie", "logined=false; Path=/ \r\n");
        httpResponse.sendRedirect("/user/login_failed.html");
    }
}
