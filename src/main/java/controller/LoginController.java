package controller;

import db.DataBase;
import model.User;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;
import webserver.http.HttpSession;

public class LoginController extends AbstractController {
    @Override
    public void doPost(HttpRequest request, HttpResponse response) throws Exception {
        User user = DataBase.findUserById(request.getParameter("userId"));
        boolean isLoginSuccess = login(user, request.getParameter("password"));

        if (isLoginSuccess) {
            HttpSession session = request.getSession();
            session.setAttribute("user", user);
        }

        response.addHeader("Set-Cookie", "logined=" + isLoginSuccess + "; Path=/");
        response.sendRedirect(afterRedirect(isLoginSuccess));
    }

    private boolean login(User user, String userPassword) {
        if (user == null) {
            return false;
        }
        return user.isSamePassword(userPassword);
    }

    private String afterRedirect(boolean isLoginSuccess) {
        String location = "/index.html";

        if (!isLoginSuccess) {
            location = "/user/login_failed.html";
        }
        return location;
    }
}
