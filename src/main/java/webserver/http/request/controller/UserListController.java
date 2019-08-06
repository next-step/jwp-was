package webserver.http.request.controller;

import db.DataBase;
import model.User;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;
import webserver.http.response.view.ModelAndView;

import java.util.Collection;
import java.util.Optional;

/**
 * @author : yusik
 * @date : 2019-08-07
 */
public class UserListController implements Controller {

    @Override
    public ModelAndView postProcess(HttpRequest httpRequest, HttpResponse httpResponse) {
        return null;
    }

    @Override
    public ModelAndView getProcess(HttpRequest httpRequest, HttpResponse httpResponse) {
        Collection<User> users = DataBase.findAll();
        return new ModelAndView(users, "/user/list");
    }
}
