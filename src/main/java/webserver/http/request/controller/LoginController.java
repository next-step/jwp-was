package webserver.http.request.controller;

import db.DataBase;
import model.User;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;
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

        if (user == null || !user.matchPassword(password)) {
            httpResponse.setCookie("logined=false; Path=/");
            return new ModelAndView("redirect::/user/login_failed.html");
        }

        httpResponse.setCookie("logined=true; Path=/");

        return new ModelAndView(ViewType.REDIRECT.getPrefix()+ "/index.html");
    }
}
