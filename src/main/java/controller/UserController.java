package controller;

import db.DataBase;
import http.request.HttpRequest;
import http.response.HttpResponse;
import model.User;

public class UserController extends AbstractController {

    public UserController() {
        super("/user/create");
    }

    @Override
    public void post(final HttpRequest request, final HttpResponse httpResponse) {
        User newUser = User.init(request.getParameters());
        DataBase.addUser(newUser);

        httpResponse.setRedirect("/index.html");
    }
}
