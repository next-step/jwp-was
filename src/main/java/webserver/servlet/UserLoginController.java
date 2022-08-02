package webserver.servlet;

import static exception.ExceptionStrings.NOT_FOUND_MEMBER;
import static webserver.servlet.UserListController.KEY_LOGINED;

import db.DataBase;
import model.User;
import utils.NullChecker;
import webserver.domain.HttpCustomSession;
import webserver.domain.HttpHeader;
import webserver.domain.Sessions;
import webserver.request.HttpRequest;
import webserver.response.HttpResponse;

public class UserLoginController implements Controller {

    @Override
    public void doPost(HttpRequest httpRequest, HttpResponse httpResponse) {
        String userId = httpRequest.getParameter("userId");
        String password = httpRequest.getParameter("password");
        validate(userId, password);

        User userFound = DataBase.findUserById(userId)
            .orElseThrow(() -> new IllegalStateException(NOT_FOUND_MEMBER));

        if (!userFound.fitPassword(password)) {
            httpResponse.addHeader(HttpHeader.SET_COOKIE, KEY_LOGINED + "=" + false + "; " + "Path=/");
            httpResponse.sendRedirect("/user/login_failed.html");
            return;
        }

        HttpCustomSession session = Sessions.INSTANCE.create();
        session.setAttribute(KEY_LOGINED, Boolean.TRUE.toString());
        httpResponse.addHeader(HttpHeader.SET_COOKIE, HttpHeader.JSESSIONID + "=" + session.getId());
//        httpResponse.addHeader(HttpHeader.SET_COOKIE, KEY_LOGINED + "=" + true + "; " + "Path=/");
        httpResponse.sendRedirect("/index.html");
    }

    private void validate(String userId, String password) {
        NullChecker.requireNonNull(userId, password);
    }

}
