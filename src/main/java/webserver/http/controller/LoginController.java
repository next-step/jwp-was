package webserver.http.controller;

import db.DataBase;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.http.domain.Cookie;
import webserver.http.domain.HttpRequest;
import webserver.http.domain.HttpResponse;
import webserver.http.domain.RequestBody;
import webserver.http.domain.session.HttpSession;


public class LoginController implements Controller {

    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Override
    public void execute(HttpRequest httpRequest, HttpResponse httpResponse) {
        RequestBody requestBody = httpRequest.requestBody();

        String userId = requestBody.body("userId");
        User user = DataBase.findUserById(userId);
        HttpSession session = new HttpSession();
        String sessionId = session.sessionId().id();

        if (user != null && user.samePassword(requestBody.body("password"))) {
            session.setAttribute(sessionId, true);
            httpResponse.addHeader("Set-Cookie", "loginKey" + "=" + sessionId);
            httpResponse.sendRedirect("/index.html");
            return;
        }

        session.setAttribute(sessionId, false);
        httpResponse.addHeader("Set-Cookie", "loginKey" + "=" + sessionId);
        httpResponse.sendRedirect("/user/login_failed.html");
    }
}
