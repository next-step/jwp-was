package controller;

import db.DataBase;
import model.*;
import model.http.HttpRequest;
import model.http.HttpResponse;
import model.http.HttpSession;

import java.util.UUID;

public class LoginController extends AbstractController {

    public static final String LOGIN_FAILED_PATH = "/user/login_failed.html";
    public static final String USER_ID = "userId";
    public static final String LOGIN_SUCCESS_PATH = "/index.html";

    @Override
    public void doPost(HttpRequest request, HttpResponse response) {
        final String userId = request.getBody().getFirstValue(USER_ID);
        final User findUser = DataBase.findUserById(userId);
        final Cookie cookie = request.getCookie("sessionId");

        String uuid = UUID.randomUUID().toString();
        HttpSession httpSession = new HttpSession(uuid);

        if (hasSessionCookie(cookie)) {
            uuid = cookie.getValue();
            if (DataBase.findSession(uuid) != null) {
                httpSession = DataBase.findSession(uuid);
            }
        }

        if (findUser == null) {
            httpSession.setAttribute("logined", "false");
            response.loginRedirect(LOGIN_FAILED_PATH, new Cookie("sessionId", uuid));
            return;
        }

        DataBase.addSession(httpSession);
        httpSession.setAttribute("logined", "true");
        response.loginRedirect(LOGIN_SUCCESS_PATH, new Cookie("sessionId", uuid));
    }

    private boolean hasSessionCookie(Cookie cookie) {
        return cookie != null && !cookie.isEmpty();
    }
}
