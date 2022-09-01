package controller;

import db.DataBase;
import model.RequestParameters;
import model.User;
import model.request.HttpRequest;
import model.response.HttpResponse;

public class UserCreateController extends AbstractController {
    public static final String REDIRECT_PATH = "index.html";

    @Override
    public void doPost(HttpRequest request, HttpResponse response) {
        RequestParameters parameters = request.getRequestParameters();
        User user = new User(parameters.getValue("userId"), parameters.getValue("password"),
                parameters.getValue("name"),
                parameters.getValue("email"));
        DataBase.addUser(user);
        response.redirect(REDIRECT_PATH);
    }
}
