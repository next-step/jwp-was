package webserver.http.request.controller;

import db.DataBase;
import model.User;
import webserver.http.common.session.HttpSession;
import webserver.http.common.session.SessionManager;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;
import webserver.http.response.header.Cookie;
import webserver.http.response.view.ModelAndView;
import webserver.http.response.view.ViewType;

/**
 * @author : yusik
 * @date : 2019-08-07
 */
public class LoginController extends AbstractController {

    public LoginController(boolean allowAll) {
        super(allowAll);
    }

    @Override
    public ModelAndView postProcess(HttpRequest httpRequest, HttpResponse httpResponse) {
        User user = DataBase.findUserById(httpRequest.getParameter("userId"));
        String password = httpRequest.getParameter("password");

        HttpSession httpSession = httpRequest.getHttpSession();

        if (user == null || !user.matchPassword(password)) {
            return new ModelAndView(ViewType.REDIRECT.getPrefix() + "/user/login_failed.html");
        }

        httpSession.setAttribute("user", user);
        Cookie cookie = new Cookie(SessionManager.SESSION_HEADER_NAME, httpSession.getId());
        cookie.setPath("/");
        httpResponse.addCookie(cookie);
        return new ModelAndView(ViewType.REDIRECT.getPrefix() + "/index.html");
    }
}
