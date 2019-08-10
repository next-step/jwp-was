package webserver.http.request.controller;

import db.DataBase;
import model.User;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;
import webserver.http.response.view.ModelAndView;
import webserver.http.response.view.ViewType;

import java.util.Collection;

/**
 * @author : yusik
 * @date : 2019-08-07
 */
public class UserListController extends AbstractController {

    public UserListController(boolean allowAll) {
        super(allowAll);
    }

    @Override
    public ModelAndView getProcess(HttpRequest httpRequest, HttpResponse httpResponse) {
        Collection<User> users = DataBase.findAll();
        return new ModelAndView(users, ViewType.TEMPLATE.getPrefix() + "/user/list");
    }
}
