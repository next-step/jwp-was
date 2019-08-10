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
public class JoinController extends AbstractController {

    public JoinController(boolean allowAll) {
        super(allowAll);
    }

    @Override
    public ModelAndView postProcess(HttpRequest httpRequest, HttpResponse httpResponse) {
        User user = new User(
                httpRequest.getParameter("userId"),
                httpRequest.getParameter("password"),
                httpRequest.getParameter("name"),
                httpRequest.getParameter("email")
        );

        DataBase.addUser(user);
        return new ModelAndView(ViewType.REDIRECT.getPrefix() + "/index.html");
    }
}
