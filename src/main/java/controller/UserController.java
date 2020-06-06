package controller;

import db.DataBase;
import http.HttpRequest;
import http.HttpResponse;
import model.User;
import sun.plugin.dom.exception.InvalidAccessException;

public class UserController extends Controller {

    public UserController() {
        super("/user/create");
    }

    @Override
    public HttpResponse get(final HttpRequest request) {
        throw new InvalidAccessException("Not support method");
    }

    @Override
    public HttpResponse post(final HttpRequest request) {
        return saveUser(request);
    }

    private HttpResponse saveUser(final HttpRequest request) {
        User newUser = User.init(request.getParameters());

        DataBase.addUser(newUser);

        return HttpResponse.redirect("/index.html");
    }
}
