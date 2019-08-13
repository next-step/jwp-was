package requesthandler;

import db.DataBase;
import model.User;
import webserver.controller.AbstractController;
import webserver.request.HttpRequest;
import webserver.request.HttpSession;
import webserver.response.HttpResponse;

import static java.util.Objects.nonNull;

/**
 * Created by hspark on 2019-08-05.
 */
public class UserLoginController extends AbstractController {
    public static final String URL = "/user/login";
    private static final String COOKIE_SESSION_KEY = "logined";

    @Override
    public void doPost(HttpRequest httpRequest, HttpResponse httpResponse) {
        HttpSession httpSession = httpRequest.getHttpSession();
        String userId = httpRequest.getParameter("userId");
        String password = httpRequest.getParameter("password");

        User user = DataBase.findUserById(userId);
        if (nonNull(user) && user.matchPassword(password)) {
            httpSession.setAttribute(COOKIE_SESSION_KEY, true);
            httpResponse.redirect("/index.html");
            return;
        }
        httpResponse.redirect("/user/login_failed.html");
        httpSession.setAttribute(COOKIE_SESSION_KEY, false);
    }
}
