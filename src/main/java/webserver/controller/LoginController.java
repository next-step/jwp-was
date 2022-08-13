package webserver.controller;

import db.DataBase;
import model.User;
import webserver.request.HttpRequest;
import webserver.response.HttpResponse;

public class LoginController implements Controller {

    @Override
    public HttpResponse service(HttpRequest request) {
        if (isLogin(request)) {
            return HttpResponse.sendRedirect("/index.html", "logined=true; Path=/");
        }
        return HttpResponse.sendRedirect("/user/login_failed.html", "logined=false");
    }

    private boolean isLogin(HttpRequest httpRequest) {
        String userId = httpRequest.getBody().getParameter("userId");
        User user = DataBase.findUserById(userId);

        if (user == null) {
            return false;
        }

        String pw = httpRequest.getBody().getParameter("password");
        return user.getPassword().equals(pw);
    }
}
