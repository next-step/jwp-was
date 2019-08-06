package webserver.http.request.controller;

import db.DataBase;
import model.User;
import webserver.http.request.HttpRequest;
import webserver.http.request.ParameterMap;
import webserver.http.response.HttpResponse;
import webserver.http.response.view.ModelAndView;

/**
 * @author : yusik
 * @date : 2019-08-07
 */
public class LoginController implements Controller {
    @Override
    public ModelAndView postProcess(HttpRequest httpRequest, HttpResponse httpResponse) {
        ParameterMap parameters = httpRequest.getParameters();
        User user = DataBase.findUserById((String) parameters.get("userId"));
        String password = (String) parameters.get("password");

        if (user == null || !user.matchPassword(password)) {
            httpResponse.setCookie("logined=false; Path=/");
            return new ModelAndView("redirect::/user/login_failed.html");
        }

        httpResponse.setCookie("logined=true; Path=/");
        return new ModelAndView("redirect::/index.html");
    }

    @Override
    public ModelAndView getProcess(HttpRequest httpRequest, HttpResponse httpResponse) {
        return null;
    }
}
