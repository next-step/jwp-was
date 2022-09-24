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

import static webserver.http.domain.session.SessionId.LOGIN_SESSION_ID;

public class LoginController implements Controller {

    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Override
    public void execute(HttpRequest httpRequest, HttpResponse httpResponse) {
        RequestBody requestBody = httpRequest.requestBody();

        String userId = requestBody.body("userId");
        User user = DataBase.findUserById(userId);
        HttpSession session = new HttpSession(LOGIN_SESSION_ID);
        Cookie cookie = Cookie.getInstance();
        cookie.addCookie("loginKey", LOGIN_SESSION_ID);

        if (user != null && user.samePassword(requestBody.body("password"))) {
            session.setAttribute(LOGIN_SESSION_ID, true);
            httpResponse.sendRedirect("/index.html");
            return;
        }

        session.setAttribute(LOGIN_SESSION_ID, false);
        httpResponse.sendRedirect("/user/login_failed.html");
    }
}
