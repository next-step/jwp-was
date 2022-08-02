package webserver.handler;

import db.DataBase;
import model.User;
import webserver.Handler;
import webserver.ModelAndView;
import webserver.http.Cookie;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;

public class LoginMemberHandler implements Handler {

    @Override
    public ModelAndView handle(HttpRequest httpRequest, HttpResponse httpResponse) {
        String userId = httpRequest.getBodyValue("userId");
        String password = httpRequest.getBodyValue("password");
        User userById = DataBase.findUserById(userId);

        if (userById != null && userById.matchPassword(password)) {
            return handleResponse(new Cookie("logined", "true", "/"), "/index.html", httpResponse);
        }

        return handleResponse(new Cookie("logined", "false", "/"), "/user/login_failed.html", httpResponse);
    }

    private ModelAndView handleResponse(Cookie cookie, String location, HttpResponse httpResponse) {
        httpResponse.addCookie(cookie);
        return new ModelAndView("redirect:" + location);
    }

}
