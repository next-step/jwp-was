package webserver.http.service;

import db.DataBase;
import model.User;
import webserver.http.request.HttpRequest;
import webserver.http.request.RequestBody;
import webserver.http.response.HttpResponse;

import java.util.Objects;

public class LoginService extends PostService {

    @Override
    protected boolean pathMatch(HttpRequest httpRequest) {
        return httpRequest.getPath().equals("/user/login");
    }

    @Override
    public void doService(HttpRequest httpRequest, HttpResponse httpResponse) {
        RequestBody requestBody = httpRequest.getRequestBody();
        String userId = requestBody.get("userId");
        String password = requestBody.get("password");

        User user = DataBase.findUserById(userId);
        if (Objects.isNull(user)) {
            throw new IllegalArgumentException("유저를 찾을수 없습니다.");
        }

        if (user.getPassword().equals(password)) {
            httpResponse.redirect("/index.html");
            httpResponse.setCookie("logined=true; Path=/");
            return;
        }
        httpResponse.redirect("/user/login_failed.html");
        httpResponse.setCookie("logined=false; Path=/");
    }
}
