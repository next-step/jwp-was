package mvc.controller;

import db.DataBase;
import exception.NotFoundException;
import http.request.protocol.Protocol;
import mvc.model.User;
import http.HttpHeader;
import http.request.HttpRequest;
import http.response.HttpResponse;
import http.response.HttpStatusCode;
import http.response.StatusLine;
import webserver.session.HttpSession;

import java.util.Map;

public class UserLoginController extends AbstractController {

    @Override
    public void doPost(HttpRequest request, HttpResponse response) {
        login(request, response);
    }

    private void login(HttpRequest request, HttpResponse response) {
        if (isValidUserInfo(request)) {
            User user = DataBase.findUserById(extractRequiredBody(request, "userId"));
            HttpSession session = request.getSession();
            session.setAttribute("user", user);
            response.sendRedirect("/index.html");
            return;
        }
        response.sendRedirect("/user/login_failed.html");
    }

    private boolean isValidUserInfo(HttpRequest request) {
        User user = DataBase.findUserById(extractRequiredBody(request, "userId"));
        return user != null && checkPasswordMatching(request, user);
    }

    private boolean checkPasswordMatching(HttpRequest request, User user) {
        return extractRequiredBody(request, "password").equals(user.getPassword());
    }

    private String extractRequiredBody(HttpRequest request, String key) {
        return request.getBody(key).orElseThrow(() -> new NotFoundException(HttpStatusCode.BAD_REQUEST));
    }
}
