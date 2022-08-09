package webserver.handler;

import db.DataBase;
import model.User;
import webserver.Handler;
import webserver.ModelAndView;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;
import webserver.http.HttpSession;

public class LoginMemberHandler implements Handler {

    @Override
    public ModelAndView handle(HttpRequest httpRequest, HttpResponse httpResponse) {
        String userId = httpRequest.getBody("userId");
        String password = httpRequest.getBody("password");
        User userById = DataBase.findUserById(userId);
        HttpSession httpSession = httpRequest.getSession();

        if (userById != null && userById.matchPassword(password)) {
            httpSession.setAttribute("logined", true);
            return new ModelAndView("redirect:/index.html");
        }

        httpSession.setAttribute("logined", false);
        return new ModelAndView("redirect:/user/login_failed.html");
    }

}
