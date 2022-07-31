package webserver.handler;

import db.DataBase;
import model.User;
import webserver.Handler;
import webserver.ModelAndView;
import webserver.http.Cookie;
import webserver.http.Request;
import webserver.http.Response;

public class LoginMemberHandler implements Handler {

    @Override
    public ModelAndView handle(Request request, Response response) {
        String userId = request.getBodyValue("userId");
        String password = request.getBodyValue("password");
        User userById = DataBase.findUserById(userId);

        if (userById != null && userById.matchPassword(password)) {
            return handleResponse(new Cookie("logined", "true", "/"), "/index.html", response);
        }

        return handleResponse(new Cookie("logined", "false", "/"), "/user/login_failed.html", response);
    }

    private ModelAndView handleResponse(Cookie cookie, String location, Response response) {
        response.addCookie(cookie);
        return new ModelAndView("redirect:" + location);
    }

}
