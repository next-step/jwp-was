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
public class JoinController extends AbstractController {

    @Override
    public ModelAndView postProcess(HttpRequest httpRequest, HttpResponse httpResponse) {
        ParameterMap parameters = httpRequest.getParameters();

        System.out.println(parameters.get("userId"));
        User user = new User(
                parameters.get("userId"),
                parameters.get("password"),
                parameters.get("name"),
                parameters.get("email")
        );

        DataBase.addUser(user);
        return new ModelAndView("redirect::/index.html");
    }
}
