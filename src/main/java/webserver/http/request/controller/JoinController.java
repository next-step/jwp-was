package webserver.http.request.controller;

import db.DataBase;
import model.User;
import webserver.http.request.HttpRequest;
import webserver.http.request.ParameterMap;
import webserver.http.response.HttpResponse;

/**
 * @author : yusik
 * @date : 2019-08-07
 */
public class JoinController implements Controller {
    @Override
    public String postProcess(HttpRequest httpRequest, HttpResponse httpResponse) {
        ParameterMap parameters = httpRequest.getParameters();
        User user = new User(
                (String) parameters.get("userId"),
                (String) parameters.get("password"),
                (String) parameters.get("name"),
                (String) parameters.get("email")
        );

        DataBase.addUser(user);
        return "redirect::/index.html";
    }

    @Override
    public String getProcess(HttpRequest httpRequest, HttpResponse httpResponse) {
        return "";
    }
}
