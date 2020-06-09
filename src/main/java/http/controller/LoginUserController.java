package http.controller;

import db.DataBase;
import http.HttpRequest;
import http.HttpResponse;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by iltaek on 2020/06/09 Blog : http://blog.iltaek.me Github : http://github.com/iltaek
 */
public class LoginUserController extends AbstractController {

    private static final Logger logger = LoggerFactory.getLogger(LoginUserController.class);

    @Override
    public void doPost(HttpRequest request, HttpResponse response) {
        User user = DataBase.findUserById(request.getParameter("userId"));
        if (user != null) {
            if (user.getPassword().equals(request.getParameter("password"))) {
                response.addHeaders("Set-Cookie", "logined=true; Path=/");
                response.redirect("/index.html");
            } else {
                response.addHeaders("Set-Cookie", "logined=false; Path=/");
                response.redirect("/user/login_failed.html");
            }
        } else {
            response.addHeaders("Set-Cookie", "logined=false; Path=/");
            response.redirect("/user/login_failed.html");
        }
    }
}
