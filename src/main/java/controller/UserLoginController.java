package controller;

import db.DataBase;
import http.request.HttpRequest;
import http.request.Parameters;
import http.response.HttpResponse;
import model.User;
import webserver.controller.AbstractController;
import webserver.session.HttpSession;

public class UserLoginController extends AbstractController {

    private UserLoginController() {
    }

    private static class Singleton {
        private static final UserLoginController instance = new UserLoginController();
    }

    public static UserLoginController getInstance() {
        return Singleton.instance;
    }

    @Override
    protected void doPost(HttpRequest httpRequest, HttpResponse httpResponse) {
        final Parameters formData = new Parameters(httpRequest.getBody());
        final String userId = formData.getValue("userId");
        final String password = formData.getValue("password");

        final User user = DataBase.findUserById(userId);

        if (user.matchPassword(password)) {
            httpResponse.response302("/index.html");
            HttpSession session = httpRequest.getSession(true);
            session.setAttribute("isAuthenticated", true);
        } else {
            httpResponse.response302("/user/login_failed.html");
        }
    }

}
