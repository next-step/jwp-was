package controller.login;

import controller.AbstractController;
import controller.user.UserCreateController;
import db.DataBase;
import http.HttpRequest;
import http.HttpResponse;
import http.HttpResponseCode;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataOutputStream;

public class UserLoginController extends AbstractController {
    private static final Logger logger = LoggerFactory.getLogger(UserLoginController.class);

    @Override
    public void doPost(HttpRequest request, HttpResponse response) {
        User user = DataBase.findUserById(request.getParameter("userId"));

        if (user == null) {
            response.addHeader("Location", "/user/login_failed.html");
            response.responseHeader(HttpResponseCode.REDIRECT_300);
            return;
        }

        if (user.getPassword().equals(request.getParameter("password"))) {
            response.addHeader("Set-Cookie","logined=true");
            response.addHeader("Location", "/index.html");
            response.responseHeader(HttpResponseCode.REDIRECT_300);
        } else {
            response.forward("/user/login_failed.html");
        }
    }
}
