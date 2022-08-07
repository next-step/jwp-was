package controller.user;

import controller.AbstractController;
import db.DataBase;
import model.User;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;

import java.util.Map;

public class LogInController extends AbstractController {

    public static final String URL = "/user/login";
    public static final String successRedirectPath = "/index.html";
    public static final String failRedirectPath = "/user/login_failed.html";

    public static final String LOGIN_SUCCESS_COOKIE = "logined=true; Path=/";
    public static final String LOGIN_FAIL_COOKIE = "logined=false; Path=/";

    @Override
    public HttpResponse doPost(HttpRequest httpRequest) {
        Map<String, String> body = httpRequest.getRequestBody().getContents();
        String userId = body.get("userId");
        String password = body.get("password");

        User user = DataBase.findUserById(userId);

        if (user.getPassword().equals(password)) {
            HttpResponse successResponse = HttpResponse.redirect(successRedirectPath);
            successResponse.getResponseHeader().setCookie(LOGIN_SUCCESS_COOKIE);
            return successResponse;
        }

        HttpResponse failResponse = HttpResponse.redirect(failRedirectPath);
        failResponse.getResponseHeader().setCookie(LOGIN_FAIL_COOKIE);
        return failResponse;
    }
}
