package controller;

import db.DataBase;
import http.request.HttpRequest;
import http.response.HttpResponse;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoginController extends AbstractController {
    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    public void doPost(HttpRequest request, HttpResponse response) {
        User user = DataBase.findUserById(request.getParameter("userId"));
        boolean isLogin = isLogin(request, user);
        response.addHeader("logined=", Boolean.toString(isLogin));
        if (isLogin) {
            response.forward("/index.html");
        } else {
            response.forward("/user/login_failed.html");
        }
        logger.info("login : {}", isLogin);
    }

    private boolean isLogin(HttpRequest request, User user) {
        return user != null && user.equalsPassword(request.getParameter("password"));
    }

}
