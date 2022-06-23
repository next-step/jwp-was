package service;

import db.DataBase;
import db.FailedLoginException;
import webserver.request.Body;
import webserver.request.HttpRequest;
import webserver.response.HttpResponse;

public class UserLoginController extends PostController {

    @Override
    public HttpResponse doPost(HttpRequest httpRequest) {
        Body body = httpRequest.getBody();
        String userId = body.get("userId");
        String password = body.get("password");

        try {
            DataBase.login(userId, password);
            HttpResponse httpResponse = HttpResponse.response302("/index.html");
            httpResponse.putCookie("logined", "true");
            return httpResponse;
        } catch (FailedLoginException e) {
            HttpResponse httpResponse = HttpResponse.response302("/user/login_failed.html");
            httpResponse.putCookie("logined", "false");
            return httpResponse;
        }
    }
}
