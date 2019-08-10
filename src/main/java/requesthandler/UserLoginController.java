package requesthandler;

import db.DataBase;
import model.User;
import webserver.controller.AbstractController;
import webserver.request.Cookie;
import webserver.request.HttpRequest;
import webserver.response.HttpResponse;

/**
 * Created by hspark on 2019-08-05.
 */
public class UserLoginController extends AbstractController {
    public static final String URL = "/user/login";

    @Override
    public void doPost(HttpRequest httpRequest, HttpResponse httpResponse) {
        String userId = httpRequest.getParameter("userId");
        String password = httpRequest.getParameter("password");

        User user = DataBase.findUserById(userId);
        if (user.matchPassword(password)) {
            httpResponse.addCookie(new Cookie("logined", "true"));
            httpResponse.redirect("/index.html");
            return;
        }
        httpResponse.redirect("/user/login_failed.html");
        httpResponse.addCookie(new Cookie("logined", "false"));
    }
}
