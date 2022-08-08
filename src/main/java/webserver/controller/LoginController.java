package webserver.controller;

import db.DataBase;
import model.User;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;
import webserver.http.HttpSession;

public class LoginController extends AbstractController{
    @Override
    public void doPost(HttpRequest httpRequest, HttpResponse httpResponse) {
        String userId = httpRequest.getParameter("userId");
        String password = httpRequest.getParameter("password");
        User user = DataBase.findUserById(userId);

        if (user == null) {
            httpResponse.sendRedirect("/user/login_failed.html");
            return;
        }
        if (user.getPassword().equals(password)) {
            HttpSession session = httpRequest.getSession();
            session.setAttribute("user",user);
            httpResponse.sendRedirect("/index.html");
        }
        if (!user.getPassword().equals(password)) {
            httpResponse.sendRedirect("/user/login_failed.html");
        }
    }
}
