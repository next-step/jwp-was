package controller;

import db.DataBase;
import model.User;
import webserver.http.*;

public class LoginController implements Controller {

    private static final Path path = Path.of("/user/login");

    @Override
    public boolean match(HttpRequest request) {
        return request.isMatch(HttpMethod.POST, path);
    }

    @Override
    public HttpResponse execute(HttpRequest request, HttpResponse response) {
        User user = DataBase.findUserById(request.getParameter("userId"));
        if (user == null || !validatePassword(request, user)) {
            return response.sendRedirectWithCookie("/user/login_failed.html", "logined=false; Path=/");
        }
        HttpSession httpSession = request.getHttpSession();
        httpSession.setAttribute("user", user);
        return response.sendRedirectWithCookie("/index.html", "logined=true; Path=/");
    }

    private boolean validatePassword(HttpRequest request, User user) {
        return user.equalsPassword(request.getParameter("password"));
    }
}
