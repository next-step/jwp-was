package controller;

import db.DataBase;
import http.ContentType;
import http.HttpRequest;
import http.HttpResponse;
import model.User;

public class UserController extends Controller {

    public UserController() {
        super("/user/create");
    }

    @Override
    public HttpResponse get(HttpRequest request) {
        User newUser = User.init(request);

        DataBase.addUser(newUser);

        return HttpResponse.ok(ContentType.TEXT, new byte[]{});
    }

    @Override
    public HttpResponse post(HttpRequest request) {
        // request.getBody

        return null;
    }
}
