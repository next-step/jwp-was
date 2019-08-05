package requesthandler;

import db.DataBase;
import model.User;
import webserver.controller.Controller;
import webserver.request.Cookie;
import webserver.request.HttpRequest;
import webserver.response.HttpResponse;

/**
 * Created by hspark on 2019-08-05.
 */
public class UserLoginController implements Controller {
    public static final String URL = "/user/login";


    @Override
    public void action(HttpRequest httpRequest, HttpResponse httpResponse) {
        String userId = httpRequest.getRequestBody().getOne("userId");
        String password = httpRequest.getRequestBody().getOne("password");

        User user = DataBase.findUserById(userId);
        if (user.matchPassword(password)) {
            httpResponse.addCookie(new Cookie("logined", "true"));
            httpResponse.redirect("/index.html");
            return;
        }
        httpResponse.redirect("/user/login_failed.html");
        httpResponse.addCookie(new Cookie("logined", "false"));
    }

    @Override
    public String getRequestUrl() {
        return URL;
    }
}
