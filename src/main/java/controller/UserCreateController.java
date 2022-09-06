package controller;

import db.DataBase;
import model.*;
import model.http.HttpRequest;
import model.http.HttpResponse;
import model.request.RequestBody;

import java.io.IOException;

public class UserCreateController extends AbstractController{

    private static final String REDIRECT_PATH = "/index.html";

    @Override
    public void doPost(HttpRequest request, HttpResponse response) throws IOException{
        RequestBody body = request.getBody();
        final User user = new User(body.getFirstValue("userId"), body.getFirstValue("password"), body.getFirstValue("name"), body.getFirstValue("email"));
        DataBase.addUser(user);

        response.redirect(REDIRECT_PATH);
    }
}
