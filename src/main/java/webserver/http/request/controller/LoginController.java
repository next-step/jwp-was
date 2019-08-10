package webserver.http.request.controller;

import db.DataBase;
import model.User;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;
import webserver.http.response.header.Cookie;
import webserver.http.response.view.ModelAndView;
import webserver.http.response.view.ViewType;

import static webserver.http.request.handler.RequestMappingHandler.LOGIN_COOKIE;

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

        if (user == null || !user.matchPassword(password)) {
            Cookie cookie = new Cookie(LOGIN_COOKIE, "false");
            cookie.setPath("/");
            httpResponse.setCookie(cookie);
            return new ModelAndView(ViewType.REDIRECT.getPrefix() + "/user/login_failed.html");
        }

        Cookie cookie = new Cookie(LOGIN_COOKIE, "true");
        cookie.setPath("/");
        httpResponse.setCookie(cookie);

        return new ModelAndView(ViewType.REDIRECT.getPrefix() + "/index.html");
    }
}
